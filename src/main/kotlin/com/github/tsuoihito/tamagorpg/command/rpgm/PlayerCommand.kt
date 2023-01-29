package com.github.tsuoihito.tamagorpg.command.rpgm

enum class PlayerCommand(val commandName: String) {
    CREATE("create"),
    DELETE("delete"),
    SET_ROLE("setrole"),
    ADD_COMPLETED_DUNGEON_NAME("adddungeon"),
    REMOVE_COMPLETED_DUNGEON_NAME("removedungeon"),
    SET_LEVEL("setlevel"),
    INFO("info");

    companion object {
        fun valueOfCommandName(commandName: String): PlayerCommand? {
            return PlayerCommand.values().find { it.commandName == commandName }
        }

        fun getCommandNames(): List<String> {
            return PlayerCommand.values().map { it.commandName }
        }

        fun getCommandNameCandidate(typing: String): List<String> {
            return getCommandNames().filter { it.startsWith(typing) }
        }
    }

}
