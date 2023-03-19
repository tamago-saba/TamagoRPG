package com.github.tsuoihito.tamagorpg;

import com.github.tsuoihito.tamagorpg.model.CommandBlockScoreResult;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreChecker {
    public static List<CommandBlockScoreResult> getCommandBlockScoreResults(
        JavaPlugin plugin
    ) {
        return plugin.getServer().getWorlds()
            .stream()
            .flatMap(w -> Arrays.stream(w.getLoadedChunks()))
            .flatMap(ScoreChecker::getCommandBlocks)
            .flatMap(ScoreChecker::getCommandBlockScoreResult)
            .collect(Collectors.toList());
    }

    public static List<CommandBlockScoreResult> getCommandBlockScoreResults(
        Location from,
        int distance
    ) {
        return getCommandBlocks(from, distance)
            .flatMap(ScoreChecker::getCommandBlockScoreResult)
            .collect(Collectors.toList());
    }

    private static Stream<CommandBlock> getCommandBlocks(
        Location from,
        int distance
    ) {
        List<CommandBlock> commandBlocks = new ArrayList<>();
        for (
            int x = from.getBlockX() - distance;
            x < from.getBlockX() + distance + 1;
            x++
        ) {
            for (
                int y = from.getBlockY() - distance;
                y < from.getBlockY() + distance + 1;
                y++
            ) {
                for (
                    int z = from.getBlockZ() - distance;
                    z < from.getBlockZ() + distance + 1;
                    z++
                ) {
                    BlockState bs =
                        from.getWorld().getBlockAt(x, y, z).getState();
                    if (bs instanceof CommandBlock) {
                        commandBlocks.add((CommandBlock) bs);
                    }
                }
            }
        }
        return commandBlocks.stream();
    }

    private static Stream<CommandBlock> getCommandBlocks(Chunk chunk) {
        List<CommandBlock> commandBlocks = new ArrayList<>();
        for (int x = 0; x < 16; x++) {
            for (
                int y = chunk.getWorld().getMinHeight();
                y < chunk.getWorld().getMaxHeight() + 1;
                y++
            ) {
                for (int z = 0; z < 16; z++) {
                    BlockState bs = chunk.getBlock(x, y, z).getState();
                    if (bs instanceof CommandBlock) {
                        commandBlocks.add((CommandBlock) bs);
                    }
                }
            }
        }
        return commandBlocks.stream();
    }

    private static Stream<CommandBlockScoreResult> getCommandBlockScoreResult(
        CommandBlock commandBlock
    ) {
        return getScores(commandBlock.getCommand()).stream().map(s ->
                new CommandBlockScoreResult(
                    s,
                    commandBlock.getWorld().getName(),
                    commandBlock.getX(),
                    commandBlock.getY(),
                    commandBlock.getZ()
                )
        );
    }

    private static List<String> getScores(String command) {
        List<String> scores = new ArrayList<>();
        Pattern pattern = Pattern.compile(
            "(?<!^\\s{0,99}#.{0,99})(((?<=scores=\\{([^\\}]{0,99},)?\\s{0,99})[^\\s]*?(?==.{0,99}?\\}))|((?<=scoreboard\\s{1,99}objectives\\s{1,99}(add|remove|modify|(setdisplay\\s{1,99}[^\\s]{1,99}?))\\s{1,99}).*?(?=(\\s|$|\")))|((?<=execute\\s{1,99}.{1,99}?\\s{1,99}store\\s{1,99}result\\s{1,99}score\\s{1,99}[^\\s]{1,99}?\\s{1,99}).*?(?=\\s{1,99}run)))"
        );
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            scores.add(matcher.group());
        }
        return scores;
    }
}
