package alexresh.vanilla;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class ShulkerRespawn implements Listener {
    private int chance;

    ShulkerRespawn(int chance){
        this.chance = chance;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e){
        Random random = new Random();
        Entity entity = e.getEntity();
        if (entity instanceof Enderman){
            if(entity.getWorld().getEnvironment() == World.Environment.THE_END){
                if(entity.getLocation().subtract(0, 1, 0).getBlock().getType().toString().contains("PURPUR")||entity.getLocation().getBlock().getType().toString().contains("PURPUR")){
                    int randomPercent = random.nextInt(100)+1;//1-100
                    if(randomPercent<=chance){
                        Location location = entity.getLocation();
                        World world = entity.getWorld();
                        e.setCancelled(true);
                        world.spawn(location, Shulker.class);
                    }
                }
            }
        }
    }
}
