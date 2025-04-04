package org.novasparkle.lunaclans.Items;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Utils.Vault;
import org.novasparkle.lunaspring.API.Menus.Items.Item;


public class BankButton extends Item {
    private final OperationType type;
    private final int money;
    public BankButton(ConfigurationSection section) {
        super(section, true);
        this.type = OperationType.valueOf(section.getString("type"));
        this.money = section.getInt("money");
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        type.operate(ClansManager.getClan((OfflinePlayer) event.getWhoClicked()), this.money, (Player) event.getWhoClicked());
//        ((AMenu) this.getMenu()).find;
    }

    private enum OperationType {
        DEPOSIT {
            @Override
            public void operate(Clan clan, int amount, Player player) {
                Economy economy = Vault.getEconomy();
                if (economy.getBalance(player) < amount) player.sendMessage(MsgManager.getMessage("bank.NotEnoughMoneyPlayer"));
                else {
                    int fullBalance = clan.getBalance() + amount;
                    if (fullBalance > ClansManager.getClan(player).getLevel().bankCapacity()) {
                        player.sendMessage(MsgManager.getMessage("bank.BankCapacityLimit"));
                        return;
                    }
                    economy.withdrawPlayer(player, amount);
                    clan.setBalance(fullBalance);
                    player.sendMessage(MsgManager.getMessage("bank.DepositedToBank").replace("[amount]", String.valueOf(amount)));

                }
            }
        },
        WITHDRAW {
            @Override
            public void operate(Clan clan, int amount, Player player) {
                if (clan.getBalance() < amount) {
                    player.sendMessage(MsgManager.getMessage("bank.NotEnoughMoneyBank").replace("[amount]", String.valueOf(amount)));
                } else {
                    clan.setBalance(clan.getBalance() - amount);
                    Vault.getEconomy().depositPlayer(player, amount);
                    player.sendMessage(MsgManager.getMessage("bank.WithdrawFromBank").replace("[amount]", String.valueOf(amount)));
                }
            }
        };
        abstract void operate(Clan clan, int amount, Player player);
    }


}
