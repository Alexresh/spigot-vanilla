package alexresh.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

class PortableInventory implements Listener {

    private ItemStack craftedItem;
    private final boolean craftingModule;
    private final boolean enderChestModule;
    PortableInventory(Plugin plugin, boolean craftingModule, boolean enderChestModule){
        this.craftingModule = craftingModule;
        this.enderChestModule = enderChestModule;
        if(craftingModule){
            NamespacedKey key = new NamespacedKey(plugin,"portable_crafting_table");
            craftedItem = new ItemStack(Material.CRAFTING_TABLE);
            ItemMeta craftedItemMeta = craftedItem.getItemMeta();
            if(craftedItemMeta!=null) {
                craftedItemMeta.setDisplayName(ChatColor.GREEN + "Portable Crafting Table");
                craftedItem.setItemMeta(craftedItemMeta);
            }
            ShapelessRecipe recipe = new ShapelessRecipe(key, craftedItem);
            recipe.addIngredient(Material.CRAFTING_TABLE);
            recipe.addIngredient(Material.STICK);
            Bukkit.addRecipe(recipe);
        }
    }
    @EventHandler
    public void onEmptyClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction()== Action.RIGHT_CLICK_AIR){
            if(Objects.equals(event.getItem(), craftedItem)){
                if(!craftingModule)
                    return;
                player.openWorkbench(null,true);
            }
            if(Objects.equals(event.getItem(), new ItemStack(Material.ENDER_CHEST))){
                if(!enderChestModule)
                    return;
                player.openInventory(event.getPlayer().getEnderChest());
            }
        }
    }
    @EventHandler
    public void onBlockClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction()== Action.RIGHT_CLICK_BLOCK){
            if(Objects.equals(event.getItem(), craftedItem)){
                if(!craftingModule)
                    return;
                event.setCancelled(true);
                player.openWorkbench(null,true);
            }
            if((Objects.equals(event.getItem(), new ItemStack(Material.ENDER_CHEST)))&&(!player.isSneaking())) {
                if(!enderChestModule)
                    return;
                event.setCancelled(true);
                player.openInventory(event.getPlayer().getEnderChest());
            }
        }
    }
}
