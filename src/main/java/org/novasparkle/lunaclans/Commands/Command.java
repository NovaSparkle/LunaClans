package org.novasparkle.lunaclans.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaspring.API.Menus.MenuManager;

import java.util.List;

;

public class Command implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                if (!ClansManager.hasClan(player))
                    MenuManager.openInventory(player, new Menu(player, EMenu.Create, null));
                else
                    MenuManager.openInventory(player, new Menu(player, EMenu.MainMenu, null));
            } else {
                sender.sendMessage(MsgManager.getMessage("noConsole"));
            }

        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            ConfigManager.reload();
            MsgManager.reload();
            EMenu.loadMenus();
            ClansManager.loadClans();
            sender.sendMessage(MsgManager.getMessage("pluginReloaded"));

        } else if (args.length == 3) {
            if (sender instanceof Player player) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (ClansManager.hasClan(player))
                        player.sendMessage(MsgManager.getMessage("hasClan"));
                    else {
                        Clan clan = new Clan(args[1], MsgManager.color(args[2]), player);
                        ClansManager.addClan(clan);
                        ClansManager.serialize(clan);
                        player.sendMessage(MsgManager.getMessage("clanCreated"));
                    }
                }
            } else {
                sender.sendMessage(MsgManager.getMessage("noConsole"));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return null;
    }
    public static boolean hasPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission(String.format("lunaclans.%s", permission)) &&
                !sender.hasPermission("lunaclans.admin")) {
            sender.sendMessage(MsgManager.getMessage("noPermission"));
            return false;
        }
        return true;
    }
}
