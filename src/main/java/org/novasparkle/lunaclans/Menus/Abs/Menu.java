package org.novasparkle.lunaclans.Menus.Abs;


import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.novasparkle.lunaclans.Items.SwitchButton;
import org.novasparkle.lunaclans.LunaClans;
import org.novasparkle.lunaspring.API.Configuration.Configuration;
import org.novasparkle.lunaspring.API.Menus.AMenu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;

import java.lang.reflect.Constructor;

public class Menu extends AMenu {
    private final EMenu eMenu;
    @Getter
    private final Menu fromMenu;
    public Menu(Player player, EMenu eMenu, @Nullable Menu fromMenu) {
        super(player);
        this.eMenu = eMenu;
        this.fromMenu = fromMenu;
        this.initialize(eMenu.getConfiguration().self(), true);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.reflectiveInsertItems();
        this.insertAllItems();
    }

    public Configuration getConfig() {
        return this.eMenu.getConfiguration();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem != null) this.findFirstItem(clickedItem).onClick(e);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {}

    @SneakyThrows
    protected void reflectiveInsertItems() {
        ConfigurationSection section = eMenu.getConfiguration().getSection("items");
        assert section != null;
        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            assert itemSection != null;
            if (itemSection.getBoolean("ignore")) {
                LunaClans.getINSTANCE().info(String.format("Кнопка %s пропущена, Меню - %s", itemSection.getName(), eMenu.name()));
                continue;
            }
            EMenu switchMenu = EMenu.getByFileName(itemSection.getString("switchMenu"));

            if (switchMenu != null) {
                this.addItems(new SwitchButton(itemSection, switchMenu, this));

            } else if (itemSection.getBoolean("reflected")) {
                Constructor<?> constructor = null;
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(String.format("org.novasparkle.lunaclans.Items.reflection.%s", itemSection.getName()));
                    if (!Item.class.isAssignableFrom(clazz)) {
                        throw new RuntimeException(String.format("Указанный класс не является кнопкой: %s", itemSection.getName()));
                    }

                    constructor = clazz.getConstructor(ConfigurationSection.class);
                    Item btn = (Item) constructor.newInstance(itemSection);
                    this.addItems(btn);

                } catch (NoSuchMethodException noSuchMethodException) {
                    try {
                        constructor = clazz.getConstructor(ConfigurationSection.class, Player.class);
                        Item btn = (Item) constructor.newInstance(itemSection, this.getPlayer());
                        this.addItems(btn);
                    } catch (NoSuchMethodException e) {
                        constructor = clazz.getConstructor(ConfigurationSection.class, Menu.class);
                        Item btn = (Item) constructor.newInstance(itemSection, this.fromMenu);
                        this.addItems(btn);
                    }

                } finally {
                    if (constructor == null) {
                        throw new RuntimeException(String.format("Неверно указано имя кнопки %s! Смотрите документацию!", itemSection.getName()));
                    }
                    LunaClans.getINSTANCE().info(String.format("Кнопка %s создана искусственно", itemSection.getName()));
                }
            } else {
                this.addItems(new Item(itemSection, true));
            }
        }
    }
}
