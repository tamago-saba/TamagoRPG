package com.github.tsuoihito.tamagorpg.command

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.command.rpgm.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class RPGManageCommandExecutor(private val plugin: TamagoRPG) : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.isEmpty()) return emptyList()
        val newArgs = args.drop(1)
        return when (args.size) {
            1 -> getFirstCandidate(args[0])
            else -> getOtherCandidate(args[0], newArgs)
        }
    }

    private fun getFirstCandidate(typing: String): List<String> {
        return listOf("dungeon", "player", "world").filter { it.startsWith(typing) }
    }

    private fun getOtherCandidate(first: String, args: List<String>): List<String> {
        return when (first) {
            "dungeon" -> getDungeonCommandCandidate(plugin, args)
            "player" -> getPlayerCommandCandidate(plugin, args)
            "world" -> getWorldCommandCandidate(plugin, args)
            else -> emptyList()
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        val newArgs = args.drop(1)
        when (args[0]) {
            "dungeon" -> executeDungeonCommand(plugin, sender, newArgs)
            "player" -> executePlayerCommand(plugin, sender, newArgs)
            "world" -> executeWorldCommand(plugin, sender, newArgs)
        }
        return true
    }
}
