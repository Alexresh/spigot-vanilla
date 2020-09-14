package alexresh.vanilla;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class BoatEject implements Listener {
    @EventHandler
    public void onPlayerShiftRightClickOnBoat(PlayerInteractEntityEvent event){
        Entity entity = event.getRightClicked();
        if((entity instanceof Boat)||(entity instanceof Minecart)){
            if(event.getPlayer().isSneaking()){
                entity.eject();
            }
        }
    }
}
