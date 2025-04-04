package org.novasparkle.lunaclans.Items.reflection;

import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Menus.MenuManager;

@Reflected
public class BackButton extends Item {
    private final Menu menu;
    public BackButton(ConfigurationSection section, Menu menu) {
        super(section, true);
        this.menu = menu;
    }
    @Override
    @SneakyThrows
    public void onClick(InventoryClickEvent event) {
        MenuManager.openInventory((Player) event.getWhoClicked(), menu);
    }
}
