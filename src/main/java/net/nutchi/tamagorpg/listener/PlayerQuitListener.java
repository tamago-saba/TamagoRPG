package net.nutchi.tamagorpg.listener;

import net.nutchi.tamagorpg.TamagoRPG;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final TamagoRPG plugin;

    public PlayerQuitListener(TamagoRPG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getAddingSharedLocationPlayers().remove(event.getPlayer());
    }
}
