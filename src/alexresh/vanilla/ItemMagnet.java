package alexresh.vanilla;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Container;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import java.util.List;

public class ItemMagnet implements Listener {
    @EventHandler
    public void onBlockDropItems(BlockDropItemEvent event){
        Player player = event.getPlayer();
        List<Item> droppedItems = event.getItems();
        if(event.getBlockState().getType().equals(Material.AIR))
            return;
        if(droppedItems.isEmpty())
            return;
        if((player.getGameMode()== GameMode.CREATIVE)||(player.getGameMode() == GameMode.SPECTATOR))
            return;
        if(event.getBlockState() instanceof Container)
            return;
        event.setCancelled(true);
        for(Item item:droppedItems){
            if(player.getInventory().firstEmpty()==-1){
                player.getWorld().dropItem(player.getLocation(),item.getItemStack());
                item.setPickupDelay(0);
            }else{
                player.getInventory().addItem(item.getItemStack());
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.2F, 1.0F);
            }

        }
    }
}
