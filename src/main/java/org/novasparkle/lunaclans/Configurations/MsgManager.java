package org.novasparkle.lunaclans.Configurations;

import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaspring.API.Configuration.Configuration;
import org.novasparkle.lunaspring.API.Util.Service.managers.ColorManager;


public class MsgManager extends ColorManager {
    private static Configuration config;
    public static void init(Configuration configuration) {
        config = configuration;
        reload();
    }
    public static void reload() {
        config.reload();
    }

    public static String getMessage(String msg) {
        ConfigurationSection section = config.getSection("messages");
        String str = section.getString(msg);
        assert str != null;
        return color(str);
    }
}
