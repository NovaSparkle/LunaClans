package org.novasparkle.lunaclans.Menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Clans.Members.Member;
import org.novasparkle.lunaclans.Items.PlayerButton;
import org.novasparkle.lunaclans.Menus.Abs.Menus;
import org.novasparkle.lunaclans.Menus.Abs.PageMenu;

import java.util.Iterator;
import java.util.List;

public class MembersList extends PageMenu<Member> {

    public MembersList(Player player, Menus menus) {
        super(player, menus);

        Clan clan = ClansManager.getClan(player);
        List<Member> allMembers = clan.getStructure().getMembers();
        this.partition(allMembers);
    }

    @Override
    public void reloadPage(int page) {
        for (int i : this.order) {
            this.getInventory().setItem(i, null);
        }
        this.setPage(page);

        ConfigurationSection section = this.getConfig().getConfigurationSection("items.playerButton");
        Iterator<Member> iter = this.allItems.get(this.getPage()).iterator();
        assert section != null;
        for (int slot: this.order) {
            if (iter.hasNext()) {
                PlayerButton playerButton = new PlayerButton(section, this.getPlayer());
                playerButton.setSlot((byte) slot);
                this.addItems(playerButton);
            }
        }
        this.insertAllItems();
    }

}
