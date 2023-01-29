package com.github.tsuoihito.tamagorpg.listener

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.player.loadPlayer
import com.github.tsuoihito.tamagorpg.player.removeRPGPlayer
import com.github.tsuoihito.tamagorpg.player.savePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerOnlineListener(private val plugin: TamagoRPG) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        loadPlayer(plugin, uuid)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        savePlayer(plugin, uuid)
        removeRPGPlayer(plugin, uuid)
    }
}
