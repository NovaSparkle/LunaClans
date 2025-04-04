package org.novasparkle.lunaclans.Menus;

import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanSelfStorage;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Menus.Abs.AComponentMenu;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;

public class SelfStorageMenu extends AComponentMenu {
    public SelfStorageMenu(Player player, EMenu eMenu, Menu fromMenu) {
        super(player, eMenu, fromMenu);
        int capacity = ClansManager.getClan(player).getLevel().playerStorageCapacity();
        if (capacity > 0) {
            this.setComponent(new ClanSelfStorage(capacity, player));
        }
    }
}