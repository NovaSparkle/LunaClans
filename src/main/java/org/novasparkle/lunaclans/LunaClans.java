package org.novasparkle.lunaclans;

import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.novasparkle.lunaclans.Commands.Command;
import org.novasparkle.lunaclans.Menus.Abs.Menus;
import org.novasparkle.lunaclans.Utils.Vault;
import org.novasparkle.lunaspring.Configuration.Configuration;
import org.novasparkle.lunaspring.Events.MenuHandler;
import org.novasparkle.lunaspring.LunaSpring;
import org.novasparkle.lunaspring.Util.ColorManager;
import org.novasparkle.lunaspring.Util.Service.ColorService;

public final class LunaClans extends JavaPlugin {
    @Getter
    private static LunaClans INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        this.saveResource("Messages.yml", false);
        this.getServer().getPluginManager().registerEvents(new MenuHandler(), this);

        PluginCommand cmd = this.getCommand("lunaclans");
        Command command = new Command();
        assert cmd != null;
        cmd.setExecutor(command);
        cmd.setTabCompleter(command);

        Menus.downloadMenus();

        ColorService service = new ColorService(new Configuration(this.getDataFolder(), "Messages").self());
        LunaSpring.getProvider().register(service);
        ColorManager.init(service);

        Vault.setupEconomy();
    }
}
