package org.novasparkle.lunaclans.Menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Menus.Abs.AComponentMenu;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;

public class ShopMenu extends AComponentMenu {

    public ShopMenu(Player player, EMenu eMenu, @Nullable Menu fromMenu) {
        super(player, eMenu, fromMenu);
        this.setComponent(ClansManager.getClan(player).getShop());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem != null) {
            Item item = this.findFirstItem(currentItem);
            if (item != null) item.onClick(event);
        }
    }
}
