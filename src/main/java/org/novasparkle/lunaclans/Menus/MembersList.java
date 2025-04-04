package org.novasparkle.lunaclans.Menus;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Clans.Members.Member;
import org.novasparkle.lunaclans.Items.PlayerButton;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaclans.Menus.Abs.PageMenu;

import java.util.Iterator;
import java.util.List;

public final class MembersList extends PageMenu<Member> {

    public MembersList(Player player, EMenu eMenu, Menu fromMenu) {
        super(player, eMenu, fromMenu);

        List<Member> allMembers = ClansManager.getClan(player).getStructure().getMembers();
        this.partition(allMembers);
    }

    @Override
    public void reloadPage(int page) {
        for (int i : this.order) {
            this.getInventory().setItem(i, null);
        }
        this.setPage(page);
        this.clear();

        ConfigurationSection section = this.getConfig().getSection("items.PlayerButton");
        Iterator<Member> iter = this.allItems.get(this.getPage() - 1).iterator();
        assert section != null;
        for (int slot: this.order) {
            if (iter.hasNext()) {
                OfflinePlayer player = iter.next().getPlayer();

                PlayerButton playerButton = new PlayerButton(section, player);
                playerButton.setSlot((byte) slot);
                this.addItems(playerButton);
            }
        }
        if (this.allItems.size() > 1 && this.getPage() - 1 != this.allItems.size()) {
            this.addItems(new NextButton(this.getConfig().getSection("items.NextButton")));
        }
        if (this.getPage() > 1) {
            this.addItems(new PreviousButton(this.getConfig().getSection("items.PreviousButton")));
        }

        this.insertAllItems();
    }

}
