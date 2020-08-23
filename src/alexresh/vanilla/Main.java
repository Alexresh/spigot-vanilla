package alexresh.vanilla;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin  {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        getServer().getPluginManager().registerEvents(new ShulkerRespawn(config.getInt("shulkerRespawn.shulkerRespawnChance")), this);
        if(config.getBoolean("shulkerInShulker"))
            getServer().getPluginManager().registerEvents(new ShulkerInShulker(), this);
        getServer().getPluginManager().registerEvents(new NonAggressiveMob(config), this);
        boolean craftingModule, enderChaestModule;
        craftingModule = config.getBoolean("portableInventoryModule.portableCraftingTable");
        enderChaestModule = config.getBoolean("portableInventoryModule.portableEnderChest");
        getServer().getPluginManager().registerEvents(new PortableInventory(this,craftingModule,enderChaestModule), this);
        if(config.getBoolean("itemMagnetModule"))
            getServer().getPluginManager().registerEvents(new ItemMagnet(), this);
    }
}
