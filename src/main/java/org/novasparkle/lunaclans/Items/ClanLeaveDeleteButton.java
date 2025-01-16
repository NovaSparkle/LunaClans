package org.novasparkle.lunaclans.Items;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaspring.Menus.Items.Item;

import java.util.Objects;

@Reflected
public class ClanLeaveDeleteButton extends Item implements Button {
    private final boolean leader;
    public ClanLeaveDeleteButton(ConfigurationSection section, Player player) {
        super(Material.getMaterial(Objects.requireNonNull(section.getString("material"))), section.getInt("amount"));
        if (ClansManager.getClan(player).getStructure().getLeader().getPlayer().equals(player)) {
            this.leader = true;
            this.setDisplayName(section.getString("displayName.delete"));
        } else {
            this.leader = false;
            this.setDisplayName(section.getString("displayName.leave"));
        }
    }

    @Override
    public void onClick(Player player) {

    }
}
