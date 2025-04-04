package org.novasparkle.lunaclans.Menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.novasparkle.lunaclans.Items.BankButton;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;

public final class Bank extends Menu {
    public Bank(Player player, EMenu eMenu, Menu fromMenu) {
        super(player, eMenu, fromMenu);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.reflectiveInsertItems();
        ConfigurationSection section = this.getConfig().getSection("BankButtons");
        assert section != null;
        for (String key : section.getKeys(false)) {
            BankButton bankButton = new BankButton(section.getConfigurationSection(key));
            this.addItems(bankButton);
        }
        this.insertAllItems();
    }
}
