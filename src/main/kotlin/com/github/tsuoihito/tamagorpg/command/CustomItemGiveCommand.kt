package com.github.tsuoihito.tamagorpg.command

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.item.CustomItem
import com.github.tsuoihito.tamagorpg.util.getOnlinePlayerNameCandidate
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class CustomItemGiveCommand(private val plugin: TamagoRPG) : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        return when (args.size) {
            1 -> getOnlinePlayerNameCandidate(plugin, args[0])
            2 -> CustomItem.values().map { it.toString().lowercase() }.filter { it.startsWith(args[1]) }
            else -> emptyList()
        }
    }

    // /custom-give PLAYER CUSTOM_ITEM [AMOUNT]
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size != 2 && args.size != 3) return false
        val player = plugin.server.getPlayer(args[0]) ?: return true
        val customItemString = args[1]
        val amount = if (args.size == 3) args[2].toIntOrNull() ?: 1 else 1
        val item = CustomItem.valueOfOrNull(customItemString.uppercase())?.toItemStack() ?: return true
        player.inventory.addItem(item.apply { setAmount(amount) })
        return true
    }
}
