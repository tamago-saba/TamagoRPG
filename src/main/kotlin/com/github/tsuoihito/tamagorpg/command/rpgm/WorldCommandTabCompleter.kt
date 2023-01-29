package com.github.tsuoihito.tamagorpg.command.rpgm

import com.github.tsuoihito.tamagorpg.TamagoRPG
import com.github.tsuoihito.tamagorpg.util.getWorldNameList

fun getWorldCommandCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    return when (args.size) {
        1 -> getFirstCandidate(args[0])
        2 -> getSecondCandidate(plugin, args)
        else -> emptyList()
    }
}

private fun getFirstCandidate(first: String): List<String> {
    return listOf("save", "reset").filter { it.startsWith(first) }
}

private fun getSecondCandidate(plugin: TamagoRPG, args: List<String>): List<String> {
    return when (args[0]) {
        "save" -> getWorldNameCandidate(plugin, args[1])
        "reset" -> getWorldNameCandidate(plugin, args[1])
        else -> emptyList()
    }
}

private fun getWorldNameCandidate(plugin: TamagoRPG, typing: String): List<String> {
    return getWorldNameList(plugin).filter { it.startsWith(typing) }
}
