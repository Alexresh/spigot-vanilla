package alexresh.vanilla;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;

public class VillagerManager implements Listener {

    @EventHandler
    public void onPlayerShiftRightClickOnVillager(PlayerInteractEntityEvent event){
        Entity clickedEntity = event.getRightClicked();
        if(clickedEntity instanceof Villager){
            Villager villager = (Villager) clickedEntity;//get villager entity
            List<MerchantRecipe> merchantRecipeList = villager.getRecipes(); // get his recipes
            merchantRecipeList.forEach(merchantRecipe -> merchantRecipe.setUses(0));
        }
    }
}
