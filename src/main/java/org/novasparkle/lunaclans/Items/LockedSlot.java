package org.novasparkle.lunaclans.Items;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaspring.API.Menus.Items.Item;


public class LockedSlot extends Item {
    public LockedSlot(ConfigurationSection section, int slot) {
        super(section, true);

        this.setSlot((byte) slot);

    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.getWhoClicked().sendMessage(MsgManager.getMessage("storage.lockedSlot"));
    }
}