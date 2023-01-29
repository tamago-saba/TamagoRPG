package com.github.tsuoihito.tamagorpg.command

import com.github.tsuoihito.tamagorpg.item.CustomItem
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class CustomItemJsonCommand : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        return when (args.size) {
            1 -> CustomItem.values().map { it.toString().lowercase() }.filter { it.startsWith(args[0]) }
            else -> emptyList()
        }
    }

    // /custom-help CUSTOM_ITEM
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size != 1) return false
        val itemJson = CustomItem.valueOfOrNull(args[0].uppercase())?.toJsonFormat() ?: return true
        // val help = "{\"text\":\"${itemJson}\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/give @p ${itemJson}\"}}"
        sender.sendMessage(itemJson)
        return true
    }
}
