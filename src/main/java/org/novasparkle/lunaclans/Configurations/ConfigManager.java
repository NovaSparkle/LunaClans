package org.novasparkle.lunaclans.Configurations;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaclans.LunaClans;
import org.novasparkle.lunaspring.API.Configuration.IConfig;

import java.util.List;

public class ConfigManager {
    @Getter
    private final static IConfig config;
    static {
        config = new IConfig(LunaClans.getINSTANCE());
    }
    public static void reload() {
        config.reload(LunaClans.getINSTANCE());
    }
    public static int getInt(String path) {
        return config.getInt(path);
    }
    public static String getString(String path) {
        return config.getString(path);
    }
    public static List<String> getStringList(String path) {
        return config.getStringList(path);
    }
    public static ConfigurationSection getSection(String path) {
        return config.getSection(path);
    }
    public static List<Integer> getIntList(String path) {
        return config.getIntList(path);
    }
    public static Material getMaterial(String path) { return config.getMaterial(path); }
}
