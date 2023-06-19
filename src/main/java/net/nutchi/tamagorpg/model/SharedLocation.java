package net.nutchi.tamagorpg.model;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SharedLocation implements ConfigurationSerializable {
    private final String name;
    private final Location location;

    public SharedLocation(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public SharedLocation(Map<String, Object> args) {
        name = (String) args.get("name");
        location = (Location) args.get("location");
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("name", name);
        serialized.put("location", location);
        return serialized;
    }
}
