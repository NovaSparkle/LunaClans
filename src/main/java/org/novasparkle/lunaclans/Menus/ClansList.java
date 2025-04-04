package org.novasparkle.lunaclans.Menus;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaclans.Menus.Abs.PageMenu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class ClansList extends PageMenu<Clan> {
    public ClansList(Player player, EMenu menu, Menu fromMenu) {
        super(player, menu, fromMenu);
        List<Clan> clans = ClansManager.getClans();
        this.partition(clans);
    }

    @Override
    public void reloadPage(int page) {
        for (int i : this.order) {
            this.getInventory().setItem(i, null);
        }
        this.clear();
        this.setPage(page);

        if (!this.allItems.isEmpty()) {
            ConfigurationSection section = this.getConfig().getSection("items.ClanItem");
            Iterator<Clan> iter = this.allItems.get(this.getPage() - 1).iterator();
            assert section != null;
            for (int slot: this.order) {
                if (iter.hasNext()) {
                    Clan clan = iter.next();

                    ClanItem clanItem = new ClanItem(section, clan);
                    clanItem.setSlot((byte) slot);
                    this.addItems(clanItem);
                }
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


    private static class ClanItem extends Item {
        public ClanItem(ConfigurationSection section, Clan clan) {
            super(Material.getMaterial(Objects.requireNonNull(section.getString("material"))), section.getInt("amount"));

            this.setDisplayName(clan.getPrefix());

            List<String> lore = section.getStringList("lore");

            lore.replaceAll(l -> l.replace("[players]", String.valueOf(clan.getStructure().getMembersCount()))
                            .replace("[leader]", clan.getStructure().getLeader().getName())
                            .replace("[level]", String.valueOf(clan.getLevel()))
                            .replace("[balance]", String.valueOf(clan.getLevel())));

            this.setLore(lore);
        }
    }
}
