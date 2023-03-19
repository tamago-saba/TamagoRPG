package com.github.tsuoihito.tamagorpg.model;

public class CommandBlockScoreResult {
    private final String name;
    private final String world;
    private final int x;
    private final int y;
    private final int z;

    public CommandBlockScoreResult(
        String name,
        String world,
        int x,
        int y,
        int z
    ) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
