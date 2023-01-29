package com.github.tsuoihito.tamagorpg.listener

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class DoubleSwordListener : Listener {
    @EventHandler
    fun onPlayerInteractAtEntity(event: PlayerInteractAtEntityEvent) {
        if (!hasDoubleSword(event.player)) return
        event.player.swingOffHand()
        event.player.attack(event.rightClicked)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!(event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)) return
        if (!isSword(event.player.inventory.itemInOffHand.type)) return
        event.player.swingOffHand()
    }

    private fun hasDoubleSword(player: Player): Boolean {
        return isSword(player.inventory.itemInMainHand.type) && isSword(player.inventory.itemInOffHand.type)
    }

    private fun isSword(material: Material): Boolean {
        return when (material) {
            Material.WOODEN_SWORD -> true
            Material.STONE_SWORD -> true
            Material.IRON_SWORD -> true
            Material.GOLDEN_SWORD -> true
            Material.DIAMOND_SWORD -> true
            Material.NETHERITE_SWORD -> true
            else -> false
        }
    }
}
