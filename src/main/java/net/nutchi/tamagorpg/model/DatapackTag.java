package net.nutchi.tamagorpg.model;

public class DatapackTag {
    private final String name;
    private final String fileName;

    public DatapackTag(String name, String fileName) {
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
