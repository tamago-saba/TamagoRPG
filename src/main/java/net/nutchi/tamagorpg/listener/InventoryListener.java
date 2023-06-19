package net.nutchi.tamagorpg.listener;

import net.nutchi.tamagorpg.TamagoRPG;
import net.nutchi.tamagorpg.util.SharedLocationManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    private final TamagoRPG plugin;

    public InventoryListener(TamagoRPG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();

        if (plugin.getOpeningSharedLocationGuis().contains(clickedInventory) || plugin.getOpeningSharedLocationGuis().contains(event.getInventory())) {
            event.setCancelled(true);
        }

        if (clickedInventory != null) {
            if (plugin.getOpeningSharedLocationGuis().contains(clickedInventory)) {
                ItemStack item = event.getCurrentItem();
                if (item != null) {
                    if (SharedLocationManager.isLocationItem(item)) {
                        if (SharedLocationManager.isDeletingLocationItem(item)) {
                            SharedLocationManager.removeEntry(plugin, event.getSlot());
                            SharedLocationManager.openGui(plugin, event.getWhoClicked());
                        } else {
                            SharedLocationManager.getLocation(plugin, event.getSlot()).ifPresent(location -> event.getWhoClicked().teleport(location));
                        }
                    }
                    if (SharedLocationManager.isAddItem(item)) {
                        Player player = (Player) event.getWhoClicked();
                        event.getWhoClicked().closeInventory();
                        plugin.getAddingSharedLocationPlayers().add(player);
                        event.getWhoClicked().sendMessage(ChatColor.GREEN + "追加する地点の名前をチャット欄に入力してください (cancelと入力でキャンセル)");
                    }
                    if (SharedLocationManager.isDeleteItem(item)) {
                        SharedLocationManager.setDeletingModeGui(clickedInventory);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        plugin.getOpeningSharedLocationGuis().remove(event.getInventory());
    }
}
