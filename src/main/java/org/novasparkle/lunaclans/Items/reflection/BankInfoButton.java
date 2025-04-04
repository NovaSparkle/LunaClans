package org.novasparkle.lunaclans.Items.reflection;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Reflected;
import org.novasparkle.lunaspring.API.Menus.Items.Item;

import java.util.List;

@Reflected
public class BankInfoButton extends Item {
    public BankInfoButton(ConfigurationSection section, Player player) {
        super(section, true);
        List<String> lore = section.getStringList("lore");
        lore.replaceAll(l -> l.replace("[balance]", String.valueOf(ClansManager.getClan(player).getBalance())));
        this.setLore(lore);
    }
}
