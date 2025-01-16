package org.novasparkle.lunaclans.Items;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Menus.Abs.Menus;
import org.novasparkle.lunaspring.Menus.IMenu;
import org.novasparkle.lunaspring.Menus.Items.Item;
import org.novasparkle.lunaspring.Menus.MenuManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SwitchButton extends Item implements Button {
    private final Menus menu;
    public SwitchButton(ConfigurationSection section, Menus menu) {
        super(section, true);
        this.menu = menu;
    }
    @Override
    public void onClick(Player player) {
        try {
            Class<?> menuClass = Class.forName(String.format("org.novasparkle.lunaclans.Menus.%s", this.menu.name()));
            if (!IMenu.class.isAssignableFrom(menuClass)) {
                throw new RuntimeException(String.format("Указанный класс не является меню: %s", menuClass.getName()));
            }
            Constructor<?> constructor = menuClass.getConstructor(Player.class, Menus.class);
            MenuManager.openInventory(player, (IMenu) constructor.newInstance(player, menu));
        } catch (ClassNotFoundException |
                 NoSuchMethodException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
