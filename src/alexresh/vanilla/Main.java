package alexresh.vanilla;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin  {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        if(config.getBoolean("bookWithoutLeather")) {
            NamespacedKey key = new NamespacedKey(this, "book_without_leather");
            ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.BOOK));
            recipe.addIngredient(5, Material.PAPER);
            Bukkit.addRecipe(recipe);
        }
        if(config.getBoolean("shulkerInShulker"))
            getServer().getPluginManager().registerEvents(new ShulkerInShulker(), this);
        getServer().getPluginManager().registerEvents(new MobManager(config), this);
        boolean craftingModule, enderChestModule;
        craftingModule = config.getBoolean("portableInventoryModule.portableCraftingTable");
        enderChestModule = config.getBoolean("portableInventoryModule.portableEnderChest");
        getServer().getPluginManager().registerEvents(new PortableInventory(this,craftingModule,enderChestModule), this);
        if(config.getBoolean("itemMagnetModule"))
            getServer().getPluginManager().registerEvents(new ItemMagnet(), this);
        if(config.getBoolean("villagerInfinityTrading"))
            getServer().getPluginManager().registerEvents(new VillagerManager(), this);
        if(config.getBoolean("boatDismount"))
            getServer().getPluginManager().registerEvents(new BoatEject(), this);
        if(config.getBoolean("zeroRepairCost"))
            getServer().getPluginManager().registerEvents(new AnvilManager(), this);
    }
}
