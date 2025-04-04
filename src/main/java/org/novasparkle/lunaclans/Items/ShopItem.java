package org.novasparkle.lunaclans.Items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Utils.Vault;
import org.novasparkle.lunaspring.API.Menus.AMenu;
import org.novasparkle.lunaspring.API.Menus.Items.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopItem extends Item {
    private final List<String> configLore;
    @Getter
    private final String identifier;
    private final int price;
    @Setter
    private int bought;
    private final int limit;
    public ShopItem(ConfigurationSection section) {
        super(section, true);
        this.price = section.getInt("price");
        this.limit = section.getInt("limit");
        this.identifier = section.getName();
        this.configLore = section.getStringList("lore");
        this.bought = section.getInt("bought");
        this.reloadLore();
    }
    public void onClick(Player player, ClickType type) {
        if (limit > 0 && this.bought >= this.limit) {
            player.sendMessage(MsgManager.getMessage("shop.limit"));

        } else if (Vault.getEconomy().getBalance(player) < this.price) {
            player.sendMessage(MsgManager.getMessage("bank.NotEnoughMoneyPlayer"));

        } else {
            switch (type) {
                case RIGHT -> this.setAmount(1);
                case LEFT -> this.setAmount(64);
            }
            ConfirmItem confirmItem = new ConfirmItem(EMenu.ShopMenu.getConfiguration().getSection("items.ConfirmItem"), this, this.getSlot());
            AMenu menu = (AMenu) this.getMenu();
            menu.addItems(confirmItem);
            confirmItem.insert(menu);
        }
    }

    @Override
    public void serialize(@NotNull ConfigurationSection section) {
        super.serialize(section);
        section.set("lore", this.configLore);
        section.set("price", this.price);
        section.set("bought", this.bought);
        section.set("limit", this.limit);
        section.set("identifier", this.identifier);
    }
    public void reloadLore() {
        List<String> newLore = new ArrayList<>(this.configLore);
        newLore.replaceAll(l -> l.replace("[stackPrice]", String.valueOf(this.price))
                .replace("[onePrice]", String.valueOf(this.price / 64))
                .replace("[bought]", String.valueOf(this.bought))
                .replace("[limit]", String.valueOf(this.limit)));
        this.setLore(newLore);
    }

    public static class ConfirmItem extends Item  {
        private final ShopItem item;
        public ConfirmItem(ConfigurationSection section, ShopItem item, int slot) {
            super(section, true);
            this.item = item;

            String type = item.getAmount() > 1 ? "стак" : "1 штуку";

            this.setDisplayName(this.getDisplayName().replace("[type]", type));
            this.setSlot((byte) slot);
            List<String> newLore = this.getLore();
            newLore.replaceAll(l -> l
                    .replace("[item]", this.item.getMaterial().toString())
                    .replace("[amount]", String.valueOf(this.item.getAmount())));
            this.setLore(newLore);
        }
        @Override
        public void onClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();
            ItemStack itemStack = this.item.getItemStack();
            itemStack.setAmount(this.item.getAmount());
            itemStack.lore(Collections.emptyList());
            player.getInventory().addItem(itemStack);
            this.item.setBought(this.item.bought + this.item.getAmount());
            Vault.getEconomy().withdrawPlayer(player, (double) this.item.price / ((double) 64 / this.item.getAmount()));
            player.sendMessage(MsgManager.getMessage("shop.success")
                    .replace("[item]", this.item.getMaterial().toString())
                    .replace("[price]", String.valueOf(this.item.price / (64 / this.item.getAmount())))
                    .replace("[amount]", String.valueOf(this.getAmount())));
            this.item.setAmount(1);
            this.item.reloadLore();
            this.item.insert(this.item.getMenu(), this.item.getSlot());
        }
    }
}