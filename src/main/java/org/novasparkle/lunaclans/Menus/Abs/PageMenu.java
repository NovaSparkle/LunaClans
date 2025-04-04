package org.novasparkle.lunaclans.Menus.Abs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.novasparkle.lunaspring.API.Menus.Items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class PageMenu<T> extends Menu {
    protected final List<List<T>> allItems = new ArrayList<>();
    @Setter
    @Getter
    private int page;
    public final List<Integer> order;
    public PageMenu(Player player, EMenu menu, Menu fromMenu) {
        super(player, menu, fromMenu);
        this.order = menu.getConfiguration().getIntList("buttonsSlotsOrder");
        if (this.order.isEmpty()) {
            throw new RuntimeException(String.format("Список слотов кнопок не может быть пустым, вернитесь в меню %s и заполните секцию buttonsSlotsOrder", menu.getConfiguration().getFile().getName()));
        }
    }
    public void partition(List<T> classifiedItems) {
        for (int i = 0; i < classifiedItems.size(); i += order.size()) {
            this.allItems.add(classifiedItems.subList(i, Math.min(i + order.size(), classifiedItems.size())));
        }
    }

    public abstract void reloadPage(int page);

    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.reloadPage(1);
        this.reflectiveInsertItems();
        this.insertAllItems();
    }

    public class NextButton extends Item {

        public NextButton(ConfigurationSection section) {
            super(section, true);
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            PageMenu.this.reloadPage(PageMenu.this.page + 1);
        }
    }

    public class PreviousButton extends Item {

        public PreviousButton(ConfigurationSection section) {
            super(section, true);
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            PageMenu.this.reloadPage(PageMenu.this.page + 1);
        }
    }
}
