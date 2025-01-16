package org.novasparkle.lunaclans.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaclans.Menus.Abs.Menus;
import org.novasparkle.lunaspring.Menus.MenuManager;

import java.util.List;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                if (!ClansManager.hasClan(player))
                    MenuManager.openInventory(player, new Menu(player, Menus.Create));
                else
                    MenuManager.openInventory(player, new Menu(player, Menus.MainMenu));
            } else {
                sender.sendMessage(MsgManager.getMessage("noConsole"));
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            ConfigManager.reload();
            MsgManager.reload();
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
