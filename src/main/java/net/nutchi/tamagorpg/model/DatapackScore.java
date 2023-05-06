package net.nutchi.tamagorpg.model;

public class DatapackScore {
    private final String name;
    private final String fileName;

    public DatapackScore(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }
}
