package net.nutchi.tamagorpg.listener;

import net.nutchi.tamagorpg.TamagoRPG;
import net.nutchi.tamagorpg.util.SharedLocationManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final TamagoRPG plugin;

    public PlayerChatListener(TamagoRPG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.getAddingSharedLocationPlayers().contains(event.getPlayer())) {
            if (!event.getMessage().equals("cancel")) {
                SharedLocationManager.addEntry(plugin, event.getMessage(), event.getPlayer().getLocation());
            }
            plugin.getAddingSharedLocationPlayers().remove(event.getPlayer());
            event.setCancelled(true);
            plugin.getServer().getScheduler().runTask(plugin, () -> SharedLocationManager.openGui(plugin, event.getPlayer()));
        }
    }
}
