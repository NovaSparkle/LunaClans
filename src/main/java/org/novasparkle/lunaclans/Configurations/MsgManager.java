package org.novasparkle.lunaclans.Configurations;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.example.novasparkle.Configuration.Configuration;
import org.novasparkle.lunaclans.LunaClans;

import java.util.HashMap;
import java.util.Map;

public class MsgManager {
    private static final Configuration config;
    static {
        LunaClans.getINSTANCE().saveResource("Messages.yml", false);
        config = new Configuration(LunaClans.getINSTANCE().getDataFolder(), "Messages.yml");
    }
    private static final Map<String, String> colorMap = new HashMap<>();
    public static void reload() {
        reloadColors();
        config.reload();
    }
    public static void reloadColors() {
        ConfigurationSection section = config.getSection("colors");
        for (String key : section.getKeys(false)) {
            colorMap.put(section.getString(key + ".abbr"), section.getString(key + ".variable"));
        }
    }
    public static String color(String text) {
        for (Map.Entry<String, String> entry : colorMap.entrySet()) {
            text = text.replaceAll((entry.getKey()), entry.getValue());
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static String getMessage(String msg) {
        return MsgManager.color(config.getString("messages" + msg));
    }
}
