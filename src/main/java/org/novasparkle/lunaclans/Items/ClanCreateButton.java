package org.novasparkle.lunaclans.Items;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaspring.Menus.Items.Item;

import static org.novasparkle.lunaclans.Commands.Command.hasPermission;

@Reflected
public class ClanCreateButton extends Item implements Button {
    public ClanCreateButton(ConfigurationSection section) {
        super(section, true);
    }

    @Override
    public String toString() {
        return String.format("""
                {
                  Material: %s
                  Amount: %d
                  Slot: %d
                           }
                """, this.getMaterial(), this.getAmount(), this.getSlot());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onClick(Player player) {
        if (hasPermission(player, "create")) {
            TextComponent component =
                    new TextComponent(MsgManager.getMessage("clickText"));
            component.setColor(ChatColor.of(MsgManager.getColor("\\{M\\}").toHex()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ConfigManager.getString("clan.createCommand")));
            TextComponent clanCreateMsg = new TextComponent(MsgManager.getMessage("createClanMessage"));
            clanCreateMsg.setColor(ChatColor.of(MsgManager.getColor("\\{S\\}").toHex()));
            player.spigot().sendMessage(clanCreateMsg, component);
        }
    }
}
