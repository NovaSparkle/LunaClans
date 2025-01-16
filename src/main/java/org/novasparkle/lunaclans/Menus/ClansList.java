package org.novasparkle.lunaclans.Menus;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Menus.Abs.Menus;
import org.novasparkle.lunaclans.Menus.Abs.PageMenu;
import org.novasparkle.lunaspring.Menus.Items.Item;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ClansList extends PageMenu<Clan> {
    public ClansList(Player player, Menus menu) {
        super(player, menu);
    }

    @Override
    public void reloadPage(int page) {
        for (int i : this.order) {
            this.getInventory().setItem(i, null);
        }
        this.setPage(page);

        ConfigurationSection section = this.getConfig().getConfigurationSection("items.ClanItem");
        Iterator<Clan> iter = this.allItems.get(this.getPage()).iterator();
        assert section != null;
        for (int slot: this.order) {
            if (iter.hasNext()) {
                ClanItem clanItem = new ClanItem(section, iter.next());
                clanItem.setSlot((byte) slot);
                this.addItems(clanItem);
            }
        }
        this.insertAllItems();
    }


    private static class ClanItem extends Item {
        public ClanItem(ConfigurationSection section, Clan clan) {
            super(Material.getMaterial(Objects.requireNonNull(section.getString("material"))), section.getInt("amount"));

            this.setDisplayName(clan.getPrefix());

            List<String> lore = section.getStringList("lore");

            lore.replaceAll(l -> l.replace("[players]", String.valueOf(clan.getStructure().getMembersCount())
                            .replace("[leader]", clan.getStructure().getLeader().getNickName()
                            .replace("[level]", String.valueOf(clan.getLevel()))
                            .replace("[balance]", String.valueOf(clan.getLevel())))));

            this.setLore(lore);
        }
    }
}
