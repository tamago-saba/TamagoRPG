package net.nutchi.tamagorpg.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import net.nutchi.tamagorpg.model.DatapackScore;
import net.nutchi.tamagorpg.model.DatapackTag;

public class DatapackChecker {
    public static List<DatapackTag> getDatapackTags(JavaPlugin plugin) {
        String[] extensions = {"mcfunction"};
        return FileUtils.listFiles(getDatapackRootDir(plugin), extensions, true).stream()
                .flatMap(DatapackChecker::getDatapackTags).collect(Collectors.toList());
    }

    private static Stream<DatapackTag> getDatapackTags(File file) {
        return Scanner.getTags(getFileContent(file)).stream()
                .map(s -> new DatapackTag(s, file.getPath()));
    }

    public static List<DatapackScore> getDatapackScores(JavaPlugin plugin) {
        String[] extensions = {"mcfunction"};
        return FileUtils.listFiles(getDatapackRootDir(plugin), extensions, true).stream()
                .flatMap(DatapackChecker::getDatapackScores).collect(Collectors.toList());
    }

    private static Stream<DatapackScore> getDatapackScores(File file) {
        return Scanner.getScores(getFileContent(file)).stream()
                .map(s -> new DatapackScore(s, file.getPath()));
    }

    private static String getFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            return "";
        }
    }

    private static File getDatapackRootDir(JavaPlugin plugin) {
        return FileUtils.getFile(plugin.getServer().getWorldContainer(), "world", "datapacks");
    }
}
