package com.github.tsuoihito.tamagorpg.model;

public class ScoreData {
    private final String name;
    private final String description;

    public ScoreData(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
