package org.novasparkle.lunaclans.Items.Menus.Abs;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.example.novasparkle.Menus.AMenu;
import org.novasparkle.lunaclans.Items.Buttons.Button;

public class Menu extends AMenu {

    public Menu(Player player, ConfigurationSection section) {
        super(player, section);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {

    }

    @Override
    public void onClick(InventoryClickEvent e) {
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem != null) {
            ((Button) findFirstItem(clickedItem)).onClick((Player) e.getWhoClicked());
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
