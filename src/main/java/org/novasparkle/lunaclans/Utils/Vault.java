package org.novasparkle.lunaclans.Utils;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class Vault {
    @Getter
    private static Economy economy = null;
    public static void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            throw new RuntimeException("No Vault Plugin!");
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            throw new RuntimeException("No Economy Provider!");
        }
        economy = rsp.getProvider();
    }
}
