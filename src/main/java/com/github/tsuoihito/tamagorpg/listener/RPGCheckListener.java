package com.github.tsuoihito.tamagorpg.listener;

import com.github.tsuoihito.tamagorpg.ScoreChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RPGCheckListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getScoreboardTags().contains("rpg_check")) {
            ScoreChecker.getCommandBlockScoreResults(event.getTo(), 5)
                .forEach(r ->
                    event.getPlayer().sendMessage(
                        "Found score '%s' at CommandBlock %w (%x, %y, %z)"
                            .replace("%s", r.getName())
                            .replace("%w", r.getWorld())
                            .replace("%x", Integer.toString(r.getX()))
                            .replace("%y", Integer.toString(r.getY()))
                            .replace("%z", Integer.toString(r.getZ()))
                    )
                );
        }
    }
}
