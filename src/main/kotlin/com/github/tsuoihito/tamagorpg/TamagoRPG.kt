package com.github.tsuoihito.tamagorpg

import com.github.tsuoihito.tamagorpg.command.CustomItemGiveCommand
import com.github.tsuoihito.tamagorpg.command.CustomItemJsonCommand
import com.github.tsuoihito.tamagorpg.command.RPGCommandExecutor
import com.github.tsuoihito.tamagorpg.command.RPGManageCommandExecutor
import com.github.tsuoihito.tamagorpg.dungeon.Dungeon
import com.github.tsuoihito.tamagorpg.dungeon.loadDungeons
import com.github.tsuoihito.tamagorpg.listener.PlayerOnlineListener
import com.github.tsuoihito.tamagorpg.placeholder.DungeonExpansion
import com.github.tsuoihito.tamagorpg.player.RPGPlayer
import com.github.tsuoihito.tamagorpg.player.loadALlPlayers
import com.github.tsuoihito.tamagorpg.player.saveAllPlayer
import com.github.tsuoihito.tamagorpg.scoreboard.RPGScoreboardTask
import com.github.tsuoihito.tamagorpg.scoreboard.clearScoreboardAll
import com.github.tsuoihito.tamagorpg.listener.DoubleSwordListener
import com.onarandombox.MultiverseCore.MultiverseCore
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class TamagoRPG : JavaPlugin() {
    var rpgPlayers: Map<UUID, RPGPlayer> = HashMap()
    var dungeons: Map<String, Dungeon> = HashMap()
    lateinit var multiverseCore: MultiverseCore

    override fun onEnable() {
        loadMultiverseCore()
        registerListeners()
        registerCommand()
        registerPlaceholder()
        loadDungeons(this)
        loadALlPlayers(this)
        startTask()
    }

    override fun onDisable() {
        clearScoreboardAll(this)
        saveAllPlayer(this)
    }

    private fun loadMultiverseCore() {
        val multiverseCorePlugin = server.pluginManager.getPlugin("Multiverse-Core")
        if (multiverseCorePlugin is MultiverseCore) {
            multiverseCore = multiverseCorePlugin
        } else {
            server.pluginManager.disablePlugin(this)
        }
    }

    private fun registerListeners() {
        val pm = server.pluginManager
        pm.registerEvents(PlayerOnlineListener(this), this)
        pm.registerEvents(DoubleSwordListener(), this)
    }

    private fun registerCommand() {
        getCommand("rpg")?.setExecutor(RPGCommandExecutor(this))
        getCommand("rpg-manage")?.setExecutor(RPGManageCommandExecutor(this))
        getCommand("custom-give")?.setExecutor(CustomItemGiveCommand(this))
        getCommand("custom-json")?.setExecutor(CustomItemJsonCommand())
    }

    private fun registerPlaceholder() {
        DungeonExpansion(this).register()
    }

    private fun startTask() {
        RPGScoreboardTask(this).runTaskTimer(this, 0, 20)
    }
}
