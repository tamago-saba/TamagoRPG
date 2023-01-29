package com.github.tsuoihito.tamagorpg.command.rpgm

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.message.getCompleteResettingWorldMessage
import com.github.tsuoihito.tamagorpg.message.getCompleteSavingWorldMessage
import com.github.tsuoihito.tamagorpg.message.getResettingWorldMessage
import com.github.tsuoihito.tamagorpg.message.getSavingWorldMessage
import com.github.tsuoihito.tamagorpg.util.Task
import com.github.tsuoihito.tamagorpg.util.resetWorld
import com.github.tsuoihito.tamagorpg.util.saveOriginalWorld
import org.bukkit.command.CommandSender

fun executeWorldCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.isEmpty()) return false
    val newArgs = args.drop(1)
    return when (args[0]) {
        "save" -> executeSaveCommand(plugin, sender, newArgs)
        "reset" -> executeResetCommand(plugin, sender, newArgs)
        "unload" -> plugin.multiverseCore.mvWorldManager.unloadWorld(newArgs[0], true)
        else -> true
    }
}

private fun executeSaveCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.isEmpty()) return false
    sender.sendMessage(getSavingWorldMessage(args[0]))
    Task {
        saveOriginalWorld(plugin, args[0])
        sender.sendMessage(getCompleteSavingWorldMessage(args[0]))
    }.runTask(plugin)
    return true
}

private fun executeResetCommand(plugin: TamagoRPG, sender: CommandSender, args: List<String>): Boolean {
    if (args.isEmpty()) return false
    sender.sendMessage(getResettingWorldMessage(args[0]))
    resetWorld(plugin, args[0])
    sender.sendMessage(getCompleteResettingWorldMessage(args[0]))
    return true
}
