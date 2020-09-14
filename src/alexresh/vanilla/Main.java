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
    }
}
