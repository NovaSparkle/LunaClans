package org.novasparkle.lunaclans.Items;


import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaspring.API.Menus.IMenu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Menus.MenuManager;

import java.lang.reflect.Constructor;

public class SwitchButton extends Item {
    private final EMenu toMenu;
    private final Menu fromMenu;
    public SwitchButton(ConfigurationSection section, EMenu toMenu, Menu fromMenu) {
        super(section, true);
        this.toMenu = toMenu;
        this.fromMenu = fromMenu;
    }
    @Override
    @SneakyThrows
    public void onClick(InventoryClickEvent event) {
        Class<?> menuClass = Class.forName(String.format("org.novasparkle.lunaclans.Menus.%s", this.toMenu.name()));
        if (!IMenu.class.isAssignableFrom(menuClass))
            throw new RuntimeException(String.format("Указанный класс не является меню: %s", menuClass.getName()));
        Constructor<?> constructor = menuClass.getConstructor(Player.class, EMenu.class, Menu.class);
        Player player = (Player) event.getWhoClicked();
        MenuManager.openInventory(player, (IMenu) constructor.newInstance(player, this.toMenu, this.fromMenu));
    }
}
