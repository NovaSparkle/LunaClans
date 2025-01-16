package org.novasparkle.lunaclans.Items;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaclans.Utils.Vault;
import org.novasparkle.lunaspring.Menus.Items.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Reflected
public class PlayerButton extends Item implements Button {

    public PlayerButton(ConfigurationSection section, OfflinePlayer player) {
        super(Material.getMaterial(Objects.requireNonNull(section.getString("material"))), section.getInt("amount"));
        this.applyBaseHead(player);
        String displayName = section.getString("displayName");
        String nick = player.getName();
        assert displayName != null;
        assert nick != null;
        this.setDisplayName(displayName.replace("[player]", nick));
        List<String> lore = section.getStringList("lore");
        String date;
        if (player.isOnline()) {
            date = MsgManager.getMessage("playerOnline");
        } else {
            String dateFormat = section.getString("dateFormat");
            assert dateFormat != null;
            date = new SimpleDateFormat(dateFormat).format(new Date(player.getLastLogin()));

        }
        lore.replaceAll(l -> l.replace("[status]", ClansManager.getClan(player).getStructure().getMemberStatus(player.getName()).getPrefix())
                .replace("[balance]", String.valueOf(Vault.getEconomy().getBalance(player)))
                .replace("[kills]", String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS)))
                .replace("[lastLogin]", date));
    }

    @Override
    public void onClick(Player player) {

    }
}
