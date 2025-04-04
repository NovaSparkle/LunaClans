package org.novasparkle.lunaclans.Items.reflection;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Util.utilities.LunaMath;

import java.util.List;
import java.util.Objects;

@Reflected
public class ClanInfoButton extends Item {
    public ClanInfoButton(ConfigurationSection section, Player player) {
        super(Material.getMaterial(Objects.requireNonNull(section.getString("material"))), section.getInt("amount"));
        this.setSlot((byte) LunaMath.getIndex(section.getInt("slot.row"), section.getInt("slot.column")));

        Clan clan = ClansManager.getClan(player);
        List<String> lore = section.getStringList("lore");
        lore.replaceAll(s -> s.replace("[leader]", clan.getStructure().getLeader().getName())
                .replace("[playersAmount]", String.valueOf(clan.getStructure().getMembersCount()))
                .replace("[level]", String.valueOf(clan.getLevel()))
                .replace("[money]", String.valueOf(clan.getBalance()))
                .replace("[status]", clan.getStructure().getMemberStatus(player.getName()).getPrefix()));

        String prefix = MsgManager.color(clan.getPrefix());

        this.setDisplayName(prefix);
        this.setLore(lore);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (ClansManager.getClan(player).getStructure().getLeader().getPlayer().equals(player)) {
            player.closeInventory();
            TextComponent prefixChangeText = new TextComponent(MsgManager.getMessage("prefixChangeText"));
            prefixChangeText.setColor(ChatColor.of(MsgManager.getColor("\\{S\\}").toHex()));
            prefixChangeText.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ConfigManager.getString("clan.changePrefix")));

            String decorString = MsgManager.getMessage("decorativeString");

            player.sendMessage(decorString);
            player.spigot().sendMessage(prefixChangeText);
            player.sendMessage(decorString);
        }
    }
}
