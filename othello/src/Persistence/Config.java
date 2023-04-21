package Persistence;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config extends Properties {
    private static final Config instance = new Config();
    private String configPath;
    private boolean loaded;
    private Config() {}
    public void setPath(String Path) {
        configPath = Path;
    }
    public void init() {
        try {
            load(new FileReader(configPath));
        } catch (IOException e) {
            System.out.println("Error Reading Config File : " + configPath);
        }
    }

    public <T> T getProperty(String K, Class<T> c) {
        if (!loaded) {
            init();
            loaded = true;
        }
        String value = getProperty(K);
        try {
            return c.getConstructor(String.class).newInstance(value);
        } catch (Exception ignored) {}
        return null;
    }
    public Color getColor(String name) {
        int r = getProperty(name + "R", Integer.class);
        int g = getProperty(name + "G", Integer.class);
        int b = getProperty(name + "B", Integer.class);
        return new Color(r, g, b);
    }
    public static Config getInstance() {
        return instance;
    }
}
