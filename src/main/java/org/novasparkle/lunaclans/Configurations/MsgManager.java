package org.novasparkle.lunaclans.Configurations;

import org.bukkit.configuration.ConfigurationSection;
import org.novasparkle.lunaclans.LunaClans;
import org.novasparkle.lunaspring.Configuration.Configuration;
import org.novasparkle.lunaspring.Util.ColorManager;

public class MsgManager extends ColorManager {
    private static final Configuration config;
    static {
        config = new Configuration(LunaClans.getINSTANCE().getDataFolder(), "Messages");
    }
    public static void reload() {
        config.reload();
    }

    public static String getMessage(String msg) {
        ConfigurationSection section = config.getSection("messages");
        String str = section.getString(msg);
        return color(str);
    }
}
