package net.nutchi.tamagorpg;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import net.nutchi.tamagorpg.listener.PlayerInteractListener;

public final class TamagoRPG extends JavaPlugin {
    private MVWorldManager mvWorldManager;

    @Override
    public void onEnable() {
        Plugin multiverseCorePlugin = getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCorePlugin instanceof MultiverseCore) {
            MultiverseCore core = (MultiverseCore) multiverseCorePlugin;
            mvWorldManager = core.getMVWorldManager();

            getCommand("rpg").setExecutor(new TamagoRPGCommand(this));
            getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        }
    }

    @Override
    public void onDisable() {}

    public MVWorldManager getMVWorldManager() {
        return mvWorldManager;
    }
}
