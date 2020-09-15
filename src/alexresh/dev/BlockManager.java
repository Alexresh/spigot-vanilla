package alexresh.dev;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.*;

public class BlockManager implements Listener {
    //locations of all opened shulkers
    public static Map<UUID, Location> shulkerBoxLocations = new HashMap<>();

    private final FileConfiguration config;

    BlockManager(FileConfiguration config){
        this.config = config;

    }
    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent event){
        if(!config.getBoolean("itemMagnet"))
            return;

        Player player = event.getPlayer();
        List<Item> droppedItems = event.getItems();
        if(event.getBlockState().getType().equals(Material.AIR))
            return;
        if(droppedItems.isEmpty())
            return;
        if((player.getGameMode() == GameMode.CREATIVE)||(player.getGameMode() == GameMode.SPECTATOR))
            return;
        if(event.getBlockState() instanceof Container)
            return;
        event.setCancelled(true);
        droppedItems.forEach(item -> {
            if(player.getInventory().firstEmpty()==-1){//if inventory is full, then spawn item in player coordinates
                player.getWorld().dropItem(player.getLocation(),item.getItemStack());
                item.setPickupDelay(0);
            }else{ //add items in player inventory
                player.getInventory().addItem(item.getItemStack());
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.2F, 1.0F);
            }
        });
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event){
        if(!config.getBoolean("zeroRepairCost"))
            return;

        ItemStack resultItem = event.getResult();
        if(resultItem == null)
            return;
        ItemMeta resultItemMeta = resultItem.getItemMeta();
        if(resultItemMeta == null)
            return;
        if(resultItemMeta instanceof Repairable){
            Repairable repairableItemMeta = (Repairable)resultItemMeta;
            if(repairableItemMeta.hasRepairCost()){
                repairableItemMeta.setRepairCost(0);
                resultItem.setItemMeta(resultItemMeta);
                event.setResult(resultItem);
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack itemInMainHand = event.getItem();
        ItemStack craftingTable = new ItemStack(Material.CRAFTING_TABLE);
        ItemStack enderChest = new ItemStack(Material.ENDER_CHEST);
        if(action == Action.RIGHT_CLICK_AIR){
            if(Objects.equals(itemInMainHand, craftingTable) && config.getBoolean("portableCraftingTable")){
                player.openWorkbench(null,true);
                return;
            }
            if(Objects.equals(itemInMainHand, enderChest) && config.getBoolean("portableEnderChest")){
                player.openInventory(event.getPlayer().getEnderChest());
                return;
            }
        }
        if(action == Action.RIGHT_CLICK_BLOCK){
            if(Objects.equals(itemInMainHand, craftingTable) && !player.isSneaking() && config.getBoolean("portableCraftingTable")){
                event.setCancelled(true);
                player.openWorkbench(null,true);
            }
            if(Objects.equals(itemInMainHand, enderChest) && !player.isSneaking() && config.getBoolean("portableEnderChest")) {
                event.setCancelled(true);
                player.openInventory(event.getPlayer().getEnderChest());
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(config.getBoolean("shulkerInShulker") && shulkerBoxLocations.containsValue(event.getBlock().getLocation())){
            event.setCancelled(true);
        }
    }
}
