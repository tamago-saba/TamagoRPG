package com.github.tsuoihito.tamagorpg.command

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.action.createProfile
import com.github.tsuoihito.tamagorpg.action.makeDungeonCompleted
import com.github.tsuoihito.tamagorpg.action.makeDungeonOver
import com.github.tsuoihito.tamagorpg.action.movePlayerToDungeon
import com.github.tsuoihito.tamagorpg.util.getCandidate
import com.github.tsuoihito.tamagorpg.util.getDungeonNamesCandidate
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class RPGCommandExecutor(private val plugin: TamagoRPG) : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        return when (args.size) {
            1 -> getFirstCandidate(args[0])
            2 -> getSecondCandidate(plugin, args.toList())
            3 -> getThirdCandidate(args.toList())
            else -> emptyList()
        }
    }

    private fun getFirstCandidate(typing: String): List<String> {
        return getCandidate(RPGCommand.getCommandNames(), typing)
    }

    private fun getSecondCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
        val cmd = RPGCommand.valueOfCommandName(args[0]) ?: return emptyList()
        return when (cmd) {
            RPGCommand.MOVE_TO_DUNGEON -> getDungeonNamesCandidate(plugin, args[1])
            RPGCommand.MAKE_DUNGEON_COMPLETED -> getDungeonNamesCandidate(plugin, args[1])
            RPGCommand.MAKE_DUNGEON_OVER -> getDungeonNamesCandidate(plugin, args[1])
            else -> emptyList()
        }
    }

    private fun getThirdCandidate(args: List<String>): List<String> {
        val cmd = RPGCommand.valueOfCommandName(args[0]) ?: return emptyList()
        return when (cmd) {
            RPGCommand.MAKE_DUNGEON_COMPLETED -> getDelayCandidate(args[2])
            RPGCommand.MAKE_DUNGEON_OVER -> getDelayCandidate(args[2])
            else -> emptyList()
        }
    }

    private fun getDelayCandidate(typing: String): List<String> {
        return getCandidate(listOf("20"), typing)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        val newArgs = args.drop(1)
        val cmd = RPGCommand.valueOfCommandName(args[0]) ?: return false
        return when (cmd) {
            RPGCommand.MOVE_TO_DUNGEON -> executeMoveToDungeonCommand(plugin, sender, newArgs)
            RPGCommand.MAKE_DUNGEON_COMPLETED -> executeDungeonCompletedCommand(plugin, newArgs)
            RPGCommand.MAKE_DUNGEON_OVER -> executeDungeonOverCommand(plugin, newArgs)
            RPGCommand.CREATE_PROFILE -> executeCreateProfile(plugin, sender)
        }
    }

    private fun executeMoveToDungeonCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
        if (args.isEmpty()) return true
        if (sender !is Player) return true
        movePlayerToDungeon(plugin, sender, args[0])
        return true
    }

    private fun executeDungeonCompletedCommand(plugin: TamagoRPG, args: List<String>): Boolean {
        if (args.size < 2) return true
        makeDungeonCompleted(plugin, args[0], args[1].toLong())
        return true
    }

    private fun executeDungeonOverCommand(plugin: TamagoRPG, args: List<String>): Boolean {
        if (args.size < 2) return true
        makeDungeonOver(plugin, args[0], args[1].toLong())
        return true
    }

    private fun executeCreateProfile(plugin: TamagoRPG, sender: CommandSender): Boolean {
        if (sender !is Player) return true
        createProfile(plugin, sender)
        return true
    }
}
