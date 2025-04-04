package org.novasparkle.lunaclans.Menus.Abs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaclans.Clans.ClanComponents.AComponent;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanSelfStorage;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanStorage;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Items.LockedSlot;
import org.novasparkle.lunaclans.Items.ShopItem;
import org.novasparkle.lunaclans.Menus.StorageMenu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Menus.MenuManager;

import java.util.Iterator;
import java.util.List;

@Getter
public abstract class AComponentMenu extends Menu {
    @Setter
    private AComponent<?> component;
    private final List<Integer> order;

    public AComponentMenu(Player player, EMenu eMenu, Menu fromMenu) {
        super(player, eMenu, fromMenu);
        this.order = this.getConfig().getIntList("order");
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        if (this.component == null) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(MsgManager.getMessage("storage.noStorage"));
            this.reopen();

        } else if (this.component instanceof ClanStorage clanStorage && clanStorage.isViewed() && !clanStorage.getViewer().equals(this.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            Player viewer = Bukkit.getPlayer(clanStorage.getViewer());
            assert viewer != null;
            event.getPlayer().sendMessage(MsgManager.getMessage("storage.hasViewer").replace("[viewer]", viewer.getName()));
            this.reopen();

        } else {
            this.order.forEach(slot -> this.getInventory().setItem(slot, null));
            this.reflectiveInsertItems();
            Iterator<?> iter = this.component.getItems().iterator();
            for (int i : this.order) {
                if (iter.hasNext()) {
                    if (iter.next() instanceof ShopItem shopItem) {
                        shopItem.setSlot((byte) i);
                        this.addItems(shopItem);
                    } else this.getInventory().setItem(i, (ItemStack) iter.next());
                } else break;
            }
            int counter = 0;

            for (int i : this.order) {
                if (counter >= this.component.getCapacity()) {
                    this.addItems(new LockedSlot(this.getConfig().getSection("items.LockedSlot"), i));
                }
                counter++;
            }
            this.insertAllItems();
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Item clickedItem = this.findFirstItem(event.getCurrentItem());
        if (clickedItem != null) {
            event.setCancelled(true);
            clickedItem.onClick(event);
        }
    }
    private void reopen() {
        if (this.component instanceof ClanSelfStorage) {
            MenuManager.openInventory(this.getPlayer(), new StorageMenu(this.getPlayer(), EMenu.StorageMenu, new Menu(this.getPlayer(), EMenu.MainMenu, null)));
        } else
            MenuManager.openInventory(this.getPlayer(), new Menu(this.getPlayer(), EMenu.MainMenu, null));
    }
}
