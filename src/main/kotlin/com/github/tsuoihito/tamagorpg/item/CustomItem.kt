package com.github.tsuoihito.tamagorpg.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class CustomItem(val displayName: String, val originalMaterial: Material, val customModelData: Int) {
    EMERALD_SWORD("エメラルドの剣", Material.DIAMOND_SWORD, 1),
    BONE_SWORD("骨の剣", Material.WOODEN_SWORD, 1),
    ELUCIDATOR("エリュシデータ", Material.IRON_SWORD, 1);

    fun toItemStack(): ItemStack {
        return ItemStack(originalMaterial).apply {
            val meta = itemMeta
            meta?.setCustomModelData(customModelData)
            meta?.setDisplayName(displayName)
            itemMeta = meta
        }
    }

    fun toJsonFormat(): String {
        return "minecraft:${
            originalMaterial.toString().lowercase()
        }{display: {Name: \"\\\"${displayName}\\\"\"}, CustomModelData: ${customModelData}}"
    }

    companion object {
        fun valueOfOrNull(value: String): CustomItem? {
            return values().find { it.toString() == value }
        }
    }
}
