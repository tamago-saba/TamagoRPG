package com.github.tsuoihito.tamagorpg.command

enum class RPGCommand(val commandName: String) {
    MOVE_TO_DUNGEON("movetodungeon"),
    MAKE_DUNGEON_COMPLETED("dungeoncompleted"),
    MAKE_DUNGEON_OVER("dungeonover"),
    CREATE_PROFILE("createprofile");

    companion object {
        fun valueOfCommandName(commandName: String): RPGCommand? {
            return RPGCommand.values().find { it.commandName == commandName }
        }

        fun getCommandNames(): List<String> {
            return RPGCommand.values().map { it.commandName }
        }
    }
}
