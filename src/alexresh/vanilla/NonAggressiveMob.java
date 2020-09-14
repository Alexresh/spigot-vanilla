package alexresh.vanilla;

import org.bukkit.Material;
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

public class NonAggressiveMob implements Listener {

    private final FileConfiguration config;

    NonAggressiveMob(FileConfiguration config){
        this.config = config;
    }


    @EventHandler
    public void mobTargetPlayer(EntityTargetEvent event){
        if(!config.getBoolean("nonAggressiveMobModule.mobsDoNotAttackThePlayerWithTheirHead"))
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
    public void onBadMobSpawn(EntitySpawnEvent event){
        if((event.getEntityType().equals(EntityType.BAT))&&(config.getBoolean("nonAggressiveMobModule.disableBatSpawn"))){
            event.setCancelled(true);
            return;
        }
        if((event.getEntityType().equals(EntityType.PHANTOM))&&(config.getBoolean("nonAggressiveMobModule.disablePhantomSpawn"))){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void mobDeath(EntityDeathEvent event){
        if(event.getEntityType() == EntityType.CREEPER){
            Random r = new Random();
            if(r.nextInt(100)+1<=config.getInt("nonAggressiveMobModule.creeperDropHead.chance")){
                event.getDrops().add(new ItemStack(Material.CREEPER_HEAD));
            }
        }
        if(event.getEntityType() == EntityType.ZOMBIE){
            Random r = new Random();
            if(r.nextInt(100)+1<=config.getInt("nonAggressiveMobModule.zombieDropHead.chance")){
                event.getDrops().add(new ItemStack(Material.ZOMBIE_HEAD));
            }
        }
        if(event.getEntityType() == EntityType.SKELETON){
            Random r = new Random();
            if(r.nextInt(100)+1<config.getInt("nonAggressiveMobModule.skeletonDropHead.chance")){
                event.getDrops().add(new ItemStack(Material.SKELETON_SKULL));
            }
        }
        if(event.getEntityType() == EntityType.PLAYER){
            Random r = new Random();
            if(r.nextInt(100)+1<config.getInt("nonAggressiveMobModule.playerDropHead.chance")){
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
