package org.novasparkle.lunaclans.Items.Buttons;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Button {
    byte getSlot();
    ItemStack getItemStack();
    void onClick(Player player);
}
