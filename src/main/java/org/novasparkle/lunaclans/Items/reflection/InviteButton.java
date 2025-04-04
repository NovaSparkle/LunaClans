package org.novasparkle.lunaclans.Items.reflection;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaspring.API.Menus.Items.Item;

@Reflected
public class InviteButton extends Item {
    public InviteButton(ConfigurationSection section) {
        super(section, true);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        TextComponent inviteText = new TextComponent(MsgManager.getMessage("inviteText"));
        inviteText.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ConfigManager.getString("clan.inviteCommand")));
        inviteText.setColor(ChatColor.of(MsgManager.getColor("\\{S\\}").toHex()));

        String decorString = MsgManager.getMessage("decorativeString");

        Player player = (Player) event.getWhoClicked();

        player.sendMessage(decorString);
        player.spigot().sendMessage(inviteText);
        player.sendMessage(decorString);
    }
}
