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

import static org.novasparkle.lunaclans.Commands.Command.hasPermission;

@Reflected
public class ClanCreateButton extends Item {
    public ClanCreateButton(ConfigurationSection section) {
        super(section, true);
    }
    @Override
    @SuppressWarnings("deprecation")
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (hasPermission(player, "create")) {
            TextComponent component =
                    new TextComponent(MsgManager.getMessage("clickText"));
            component.setColor(ChatColor.of(MsgManager.getColor("\\{M\\}").toHex()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ConfigManager.getString("clan.createCommand")));

            player.sendMessage(MsgManager.getMessage("decorativeString"));
            player.spigot().sendMessage(component);
            player.sendMessage(MsgManager.getMessage("decorativeString"));

        }
    }
}
