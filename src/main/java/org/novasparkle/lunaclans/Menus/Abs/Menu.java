package org.novasparkle.lunaclans.Menus.Abs;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaclans.Items.Button;
import org.novasparkle.lunaclans.Items.SwitchButton;
import org.novasparkle.lunaspring.Menus.AMenu;
import org.novasparkle.lunaspring.Menus.Items.Item;
import org.novasparkle.lunaspring.Util.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Menu extends AMenu {
    private final Menus menu;
    public Menu(Player player, Menus menu) {
        super(player);
        this.menu = menu;
        this.initialize(menu.getConfiguration().self(), true);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.reflectiveInsertItems();
        this.insertAllItems();
    }

    public FileConfiguration getConfig() {
        return this.menu.getConfiguration().self();
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem != null) {
            Item item = this.findFirstItem(clickedItem);
            System.out.println(item.getClass().getName());
            if (item instanceof Button) {
                System.out.println("+");
                ((Button) item).onClick(this.getPlayer());
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {}
    protected void reflectiveInsertItems() {
        ConfigurationSection section = menu.getConfiguration().getSection("items");
        assert section != null;
        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            assert itemSection != null;
            Menus switchMenu = Menus.getByFileName(itemSection.getString("switchMenu"));
            if (itemSection.getBoolean("ignore")) {
                Utils.info(String.format("Кнопка %s пропущена, Меню - %s", itemSection.getName(), menu.name()));
                continue;
            }
            if (switchMenu != null) {
                this.addItems(new SwitchButton(itemSection, switchMenu));
            } else if (itemSection.getBoolean("reflected")) {
                try {
                    Class<?> clazz = Class.forName(String.format("org.novasparkle.lunaclans.Items.%s", itemSection.getName()));
                    if (!Button.class.isAssignableFrom(clazz)) {
                        throw new RuntimeException(String.format("Указанный класс не является кнопкой: %s", itemSection.getName()));
                    }
                    Constructor<?> constructor = clazz.getConstructor(ConfigurationSection.class);
                    Item btn = (Item) constructor.newInstance(itemSection);
                    this.addItems(btn);
                    Utils.info(String.format("Кнопка %s создана искусственно", itemSection.getName()));
                } catch (ClassNotFoundException |
                         NoSuchMethodException |
                         InvocationTargetException |
                         InstantiationException |
                         IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                this.addItems(new Item(itemSection, true));
            }
        }
    }
}
