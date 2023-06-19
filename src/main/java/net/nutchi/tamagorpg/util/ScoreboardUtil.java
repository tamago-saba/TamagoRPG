package net.nutchi.tamagorpg.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Optional;

public class ScoreboardUtil {
    public static void increaseScore(JavaPlugin plugin, String objectName, String entry) {
        getScoreboardScore(plugin, objectName, entry).ifPresent(s -> s.setScore(s.getScore() + 1));
    }

    private static Optional<Score> getScoreboardScore(JavaPlugin plugin, String objectName, String entry) {
        ScoreboardManager manager = plugin.getServer().getScoreboardManager();
        if (manager != null) {
            Objective objective = manager.getMainScoreboard().getObjective(objectName);
            if (objective != null) {
                return Optional.of(objective.getScore(entry));
            }
        }
        return Optional.empty();
    }
}
