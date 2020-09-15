package alexresh.dev;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Initial extends JavaPlugin {

    @Override
    public void onEnable() {
        FileConfiguration config = initFileConfiguration();

        //add all listeners
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new BlockManager(config));
        listeners.add(new InventoryManager(config));
        listeners.add(new EntityManager(config));
        enableListeners(listeners);

        //add recipes
        new RecipeManager(this, config);

    }

    private FileConfiguration initFileConfiguration(){
        saveDefaultConfig();
        return getConfig();
    }

    private void enableListeners(List<Listener> listeners){
        PluginManager pluginManager = getServer().getPluginManager();
        listeners.forEach(listener -> pluginManager.registerEvents(listener,this));
    }
}
