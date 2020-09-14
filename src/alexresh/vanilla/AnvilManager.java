package alexresh.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class AnvilManager implements Listener {
    @EventHandler
    public void prepareAnvilEvent(PrepareAnvilEvent event){
        ItemStack resultItem = event.getResult();
        if(resultItem == null) return;
        ItemMeta resultItemMeta = resultItem.getItemMeta();
        if(resultItemMeta == null) return;
        if(resultItemMeta instanceof Repairable){
            Repairable repairableItemMeta = (Repairable)resultItemMeta;
            if(repairableItemMeta.hasRepairCost()){
                repairableItemMeta.setRepairCost(0);
                resultItem.setItemMeta(resultItemMeta);
                event.setResult(resultItem);
            }
        }
    }
}
