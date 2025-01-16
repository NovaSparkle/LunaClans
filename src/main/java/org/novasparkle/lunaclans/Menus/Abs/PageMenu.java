package org.novasparkle.lunaclans.Menus.Abs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.novasparkle.lunaclans.Items.Button;
import org.novasparkle.lunaspring.Menus.Items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class PageMenu<T> extends Menu {
    protected final List<List<T>> allItems = new ArrayList<>();
    @Setter
    @Getter
    private int page;
    public final List<Integer> order;
    public PageMenu(Player player, Menus menu) {
        super(player, menu);
        this.order = menu.getConfiguration().getIntList("buttonsSlotsOrder");
    }
    public void partition(List<T> classifiedItems) {
        for (int i = 0; i < classifiedItems.size(); i += order.size()) {
            this.allItems.add(classifiedItems.subList(i, Math.min(i + order.size(), classifiedItems.size())));
        }
    }

    public abstract void reloadPage(int page);

    @Override
    public void onOpen(InventoryOpenEvent event) {
        reloadPage(1);
        if (this.allItems.size() > 1) {
            this.addItems(new NextButton(this.getConfig().getConfigurationSection("items.NextButton")));
        }
        if (this.page > 1) {
            this.addItems(new PreviousButton(this.getConfig().getConfigurationSection("items.PreviousButton")));
        }
        this.reflectiveInsertItems();
        this.insertAllItems();
    }

    public class NextButton extends Item implements Button {

        public NextButton(ConfigurationSection section) {
            super(section, true);
        }

        @Override
        public void onClick(Player player) {
            PageMenu.this.reloadPage(PageMenu.this.page + 1);
        }
    }

    public class PreviousButton extends Item implements Button {

        public PreviousButton(ConfigurationSection section) {
            super(section, true);
        }

        @Override
        public void onClick(Player player) {
            PageMenu.this.reloadPage(PageMenu.this.page + 1);
        }
    }
}
