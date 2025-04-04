package org.novasparkle.lunaclans.Menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanStorage;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Menus.Abs.AComponentMenu;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;

import java.util.ArrayList;
import java.util.List;

public class StorageMenu extends AComponentMenu {
    public StorageMenu(Player player, EMenu eMenu, @Nullable Menu fromMenu) {
        super(player, eMenu, fromMenu);

        this.setComponent(ClansManager.getClan(player).getStorage());
        ((ClanStorage) this.getComponent()).open(player.getUniqueId());
    }
    @Override
    public void onClose(InventoryCloseEvent event) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i : this.getOrder()) {
            ItemStack item = this.getInventory().getItem(i);
            if (findFirstItem(item) != null) continue;
            if (item != null) {
                itemStackList.add(item);
            }
        }
        ClanStorage clanStorage = (ClanStorage) this.getComponent();
        clanStorage.setItems(itemStackList, "StorageItems");
        clanStorage.close();
    }
}
