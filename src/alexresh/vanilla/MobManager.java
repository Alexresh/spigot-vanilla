package alexresh.vanilla;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Random;

public class MobManager implements Listener {

    private final FileConfiguration config;

    MobManager(FileConfiguration config){
        this.config = config;
    }


    @EventHandler
    public void mobTargetPlayer(EntityTargetEvent event){
        if(!config.getBoolean("mobModule.mobsDoNotAttackThePlayerWithTheirHead"))
            return;
        Entity mob = event.getEntity();
        Entity target = event.getTarget();
        if(target instanceof Player){
            ItemStack head = ((Player) target).getInventory().getHelmet();
            if(head!=null){
                if(mob instanceof Creeper && head.equals(new ItemStack(Material.CREEPER_HEAD)))
                    event.setCancelled(true);
                if(mob instanceof Zombie && head.equals(new ItemStack(Material.ZOMBIE_HEAD)))
                    event.setCancelled(true);
                if(mob instanceof Skeleton && head.equals(new ItemStack(Material.SKELETON_SKULL)))
                    event.setCancelled(true);
                if(mob instanceof WitherSkeleton && head.equals(new ItemStack(Material.WITHER_SKELETON_SKULL)))
                    event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event){
        EntityType entityType = event.getEntityType();
        //disable bat spawn
        if((entityType.equals(EntityType.BAT))&&(config.getBoolean("mobModule.disableBatSpawn"))){
            event.setCancelled(true);
            return;
        }
        //disable phantom spawn
        if((entityType.equals(EntityType.PHANTOM))&&(config.getBoolean("mobModule.disablePhantomSpawn"))){
            event.setCancelled(true);
            return;
        }
        //charged creeper spawn
        if(entityType.equals(EntityType.CREEPER)){
            Random r = new Random();
            int chance = config.getInt("mobModule.chargedCreeperSpawnChance");
            int rand = r.nextInt(100)+1;
            if(rand<=chance){
                ((Creeper)event.getEntity()).setPowered(true);
            }
        }
        //shulker respawn
        if (entityType.equals(EntityType.ENDERMAN)){
            Entity enderman = event.getEntity();
            Random random = new Random();
            if(enderman.getWorld().getEnvironment() == World.Environment.THE_END){
                if(enderman.getLocation().subtract(0, 1, 0).getBlock().getType().toString().contains("PURPUR")||enderman.getLocation().getBlock().getType().toString().contains("PURPUR")){
                    int randomPercent = random.nextInt(100)+1;//1-100
                    int chance = config.getInt("shulkerRespawnChance");
                    if(randomPercent<=chance){
                        Location location = enderman.getLocation();
                        World world = enderman.getWorld();
                        event.setCancelled(true);
                        world.spawn(location, Shulker.class);
                    }
                }
            }
        }
    }

    @EventHandler
    public void mobDeath(EntityDeathEvent event){
        if(event.getEntityType() == EntityType.CREEPER){
            Random r = new Random();
            if(r.nextInt(100)+1<=config.getInt("mobModule.creeperDropHead.chance")){
                event.getDrops().add(new ItemStack(Material.CREEPER_HEAD));
            }
        }
        if(event.getEntityType() == EntityType.ZOMBIE){
            Random r = new Random();
            if(r.nextInt(100)+1<=config.getInt("mobModule.zombieDropHead.chance")){
                event.getDrops().add(new ItemStack(Material.ZOMBIE_HEAD));
            }
        }
        if(event.getEntityType() == EntityType.SKELETON){
            Random r = new Random();
            if(r.nextInt(100)+1<config.getInt("mobModule.skeletonDropHead.chance")){
                event.getDrops().add(new ItemStack(Material.SKELETON_SKULL));
            }
        }
        if(event.getEntityType() == EntityType.PLAYER){
            Random r = new Random();
            if(r.nextInt(100)+1<config.getInt("mobModule.playerDropHead.chance")){
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
                if(meta!=null){
                    meta.setOwningPlayer((Player)event.getEntity());
                    playerHead.setItemMeta(meta);
                    event.getDrops().add(playerHead);
                }
            }
        }
    }
}
