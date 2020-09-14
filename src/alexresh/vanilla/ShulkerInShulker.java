package alexresh.vanilla;

import org.bukkit.*;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShulkerInShulker implements Listener {

    private final Map<UUID, Location> shulkerBoxLocations = new HashMap<>();

    @EventHandler
    public void openShulker(InventoryOpenEvent event){
        HumanEntity player = event.getPlayer();
        if(event.getInventory().getType().equals(InventoryType.SHULKER_BOX)){
            Location shulkerLocation = event.getInventory().getLocation();
            if(!shulkerBoxLocations.containsValue(shulkerLocation)){
                if(shulkerLocation !=null)
                {
                    ShulkerBox shulkerStateMeta = (ShulkerBox) shulkerLocation.getBlock().getState();
                    String title = shulkerStateMeta.getCustomName() == null ? "Shulker Box" : shulkerStateMeta.getCustomName();
                    Inventory inv = Bukkit.createInventory(null, 27, title);
                    inv.setContents(event.getInventory().getContents());
                    player.openInventory(inv);
                    shulkerBoxLocations.put(player.getUniqueId(),event.getInventory().getLocation());
                }
            }else{
                event.getPlayer().sendMessage(ChatColor.GRAY+"closed");
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void closeShulker(InventoryCloseEvent event){
        HumanEntity player = event.getPlayer();
        if (shulkerBoxLocations.containsKey(player.getUniqueId())) {
            ItemStack[] items = event.getInventory().getContents();
            if(shulkerBoxLocations.get(player.getUniqueId()).getBlock().getState() instanceof ShulkerBox){
                ShulkerBox shulker = (ShulkerBox) shulkerBoxLocations.get(player.getUniqueId()).getBlock().getState();
                shulker.getInventory().setContents(items);
                shulkerBoxLocations.remove(player.getUniqueId());
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1.0F, 1.0F);
            }
        }
    }
    @EventHandler
    public void manipulateShulker(BlockBreakEvent event){
        if(shulkerBoxLocations.containsValue(event.getBlock().getLocation())){
            event.setCancelled(true);
        }
    }
}
