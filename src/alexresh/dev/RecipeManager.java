package alexresh.dev;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class RecipeManager {

    RecipeManager(Plugin plugin, FileConfiguration config){
        if(config.getBoolean("bookWithoutLeather")) {
            NamespacedKey key = new NamespacedKey(plugin, "book_without_leather");
            ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.BOOK));
            recipe.addIngredient(5, Material.PAPER);
            Bukkit.addRecipe(recipe);
        }
    }

}
