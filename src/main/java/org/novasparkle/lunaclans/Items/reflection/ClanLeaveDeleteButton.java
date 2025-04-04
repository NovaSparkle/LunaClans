package org.novasparkle.lunaclans.Items.reflection;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Util.utilities.LunaMath;

import java.util.Objects;

@Reflected
public class ClanLeaveDeleteButton extends Item {
    private final boolean leader;
    public ClanLeaveDeleteButton(ConfigurationSection section, Player player) {
        super(Material.getMaterial(Objects.requireNonNull(section.getString("material"))), section.getInt("amount"));

        this.setSlot((byte) LunaMath.getIndex(section.getInt("slot.row"), section.getInt("slot.column")));

        if (ClansManager.getClan(player).getStructure().getLeader().getPlayer().equals(player)) {
            this.leader = true;
            this.setDisplayName(section.getString("displayName.delete"));
        } else {
            this.leader = false;
            this.setDisplayName(section.getString("displayName.leave"));
        }
    }
}
