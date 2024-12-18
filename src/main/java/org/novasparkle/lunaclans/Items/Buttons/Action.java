package org.novasparkle.lunaclans.Items.Buttons;

import org.bukkit.entity.Player;
import org.example.novasparkle.Menus.IMenu;
import org.example.novasparkle.Menus.MenuManager;

public enum Action {
    CREATE_CLAN() {
        @Override
        void onClick(Player player) {

        }
    },
    SHOW_CLANLIST() {
        @Override
        void onClick(Player player) {

        }
    },

    SHOW_CLAN_MEMBERS() {
        @Override
        void onClick(Player player) {

        }
    },
    BANK() {
        @Override
        void onClick(Player player) {

        }
    },
    LEVEL_UP() {
        @Override
        void onClick(Player player) {

        }
    },
    STORAGE() {
        @Override
        void onClick(Player player) {

        }
    },
    SHOP() {
        @Override
        void onClick(Player player) {

        }
    },
    SPAWNER() {
        @Override
        void onClick(Player player) {

        }
    },
    LEAVE() {
        @Override
        void onClick(Player player) {

        }
    },
    INVITE() {
        @Override
        void onClick(Player player) {

        }
    },
    CLAN_HOME() {
        @Override
        void onClick(Player player) {

        }
    },

    BACK() {
        @Override
        void onClick(Player player) {

        }
    },
    DEPOSIT_BANK() {
        @Override
        void onClick(Player player) {

        }
    },
    WITHDRAW_BACK() {
        @Override
        void onClick(Player player) {

        }
    },

    EDIT_PLAYER() {
        @Override
        void onClick(Player player) {

        }
    },
    PROMOTE() {
        @Override
        void onClick(Player player) {

        }
    },
    DEMOTE() {
        @Override
        void onClick(Player player) {

        }
    },
    KICK() {
        @Override
        void onClick(Player player) {

        }
    },
    HAND_OVER_CLAN() {
        @Override
        void onClick(Player player) {

        }
    },

    CLOSE() {
        @Override
        void onClick(Player player) {

        }
    };

    public void switchMenu(IMenu menu, Player player) {
        MenuManager.openInventory(player, menu);
    }
    abstract void onClick(Player player);
}