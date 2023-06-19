package net.nutchi.tamagorpg;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.nutchi.tamagorpg.command.SharedLocationCommand;
import net.nutchi.tamagorpg.command.TamagoRPGCommand;
import net.nutchi.tamagorpg.listener.InventoryListener;
import net.nutchi.tamagorpg.listener.PlayerChatListener;
import net.nutchi.tamagorpg.listener.PlayerInteractListener;
import net.nutchi.tamagorpg.listener.PlayerQuitListener;
import net.nutchi.tamagorpg.model.SharedLocation;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TamagoRPG extends JavaPlugin {
    private final List<SharedLocation> sharedLocations = new ArrayList<>();
    private final List<Inventory> openingSharedLocationGuis = new ArrayList<>();
    private final List<Player> addingSharedLocationPlayers = new ArrayList<>();
    private MVWorldManager mvWorldManager;

    @Override
    public void onEnable() {
        Plugin multiverseCorePlugin = getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCorePlugin instanceof MultiverseCore) {
            MultiverseCore core = (MultiverseCore) multiverseCorePlugin;
            mvWorldManager = core.getMVWorldManager();

            getCommand("rpg").setExecutor(new TamagoRPGCommand(this));
            getCommand("shared-location").setExecutor(new SharedLocationCommand(this));
            PluginManager pm = getServer().getPluginManager();
            pm.registerEvents(new PlayerInteractListener(this), this);
            pm.registerEvents(new InventoryListener(this), this);
            pm.registerEvents(new PlayerChatListener(this), this);
            pm.registerEvents(new PlayerQuitListener(this), this);

            ConfigurationSerialization.registerClass(SharedLocation.class);
        }

        loadSharedLocations();
    }

    @Override
    public void onDisable() {
        saveSharedLocations();
    }

    private void loadSharedLocations() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getSharedLocationsFile());
        List<?> maybeSharedLocationConfigSection = config.getList("sharedLocations");
        if (maybeSharedLocationConfigSection != null) {
            sharedLocations.addAll(maybeSharedLocationConfigSection.stream().map(e -> (SharedLocation) e).collect(Collectors.toList()));
        }
    }

    private void saveSharedLocations() {
        YamlConfiguration config = new YamlConfiguration();
        config.set("sharedLocations", sharedLocations);
        try {
            config.save(getSharedLocationsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getSharedLocationsFile() {
        return new File(getDataFolder(), "share-locations.yml");
    }

    public MVWorldManager getMVWorldManager() {
        return mvWorldManager;
    }

    public List<SharedLocation> getSharedLocations() {
        return sharedLocations;
    }

    public List<Inventory> getOpeningSharedLocationGuis() {
        return openingSharedLocationGuis;
    }

    public List<Player> getAddingSharedLocationPlayers() {
        return addingSharedLocationPlayers;
    }
}
