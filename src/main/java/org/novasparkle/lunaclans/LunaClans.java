package org.novasparkle.lunaclans;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class LunaClans extends JavaPlugin {
    @Getter
    private static LunaClans INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {

    }
}
