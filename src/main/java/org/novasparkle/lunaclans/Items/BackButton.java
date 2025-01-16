package org.novasparkle.lunaclans.Items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaspring.Menus.Items.Item;
import org.novasparkle.lunaspring.Menus.MenuManager;

public class BackButton extends Item implements Button {
    private final Menu menu;
    public BackButton(ConfigurationSection section, Menu menu) {
        super(section, true);
        this.menu = menu;
    }

    @Override
    public void onClick(Player player) {
        MenuManager.openInventory(player, this.menu);
    }
}
