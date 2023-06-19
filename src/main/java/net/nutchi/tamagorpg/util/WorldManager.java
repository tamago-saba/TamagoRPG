package net.nutchi.tamagorpg.util;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.nutchi.tamagorpg.TamagoRPG;
import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class WorldManager {
    public static boolean saveWorld(JavaPlugin plugin, String world) {
        try {
            World bukkitWorld = plugin.getServer().getWorld(world);
            if (getLoadedWorld(plugin.getServer().getWorldContainer(), world).exists()
                    && bukkitWorld != null) {
                bukkitWorld.save();

                getSavedWorldContainer(plugin.getDataFolder()).mkdir();

                File savedWorld = getSavedWorld(plugin.getDataFolder(), world);

                if (savedWorld.exists()) {
                    FileUtils.deleteDirectory(savedWorld);
                }

                FileUtils.copyDirectory(
                        getLoadedWorld(plugin.getServer().getWorldContainer(), world), savedWorld);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean restoreWorld(TamagoRPG plugin, String world) {
        return restoreWorld(plugin, plugin.getMVWorldManager(), world);
    }

    public static boolean restoreWorld(JavaPlugin plugin, MVWorldManager mvWorldManager,
                                       String world) {
        try {
            File loadedWorld = getLoadedWorld(plugin.getServer().getWorldContainer(), world);
            File savedWorld = getSavedWorld(plugin.getDataFolder(), world);

            if (savedWorld.exists() && mvWorldManager.unloadWorld(world)) {
                if (loadedWorld.exists()) {
                    FileUtils.deleteDirectory(loadedWorld);
                }
                FileUtils.copyDirectory(savedWorld, loadedWorld);
                return mvWorldManager.loadWorld(world);
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static File getLoadedWorld(File worldContainer, String world) {
        return new File(worldContainer, world);
    }

    private static File getSavedWorld(File dataFolder, String world) {
        return new File(getSavedWorldContainer(dataFolder), world);
    }

    private static File getSavedWorldContainer(File dataFolder) {
        return new File(dataFolder, "worlds");
    }
}
