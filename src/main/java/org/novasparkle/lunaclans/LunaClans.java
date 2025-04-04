package org.novasparkle.lunaclans;


import lombok.Getter;
import org.novasparkle.lunaclans.Clans.Listeners.Events;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Clans.Members.StatusManager;
import org.novasparkle.lunaclans.Commands.Command;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Utils.Vault;
import org.novasparkle.lunaspring.API.Configuration.Configuration;
import org.novasparkle.lunaspring.LunaPlugin;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ListIterator;


public final class LunaClans extends LunaPlugin {
    @Getter
    private static LunaClans INSTANCE;
    public LunaClans() {
        super();
    }
    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        this.loadFile("Messages.yml");

        this.registerTabExecutor(new Command(), "lunaclans");
        this.initialize();
        this.registerListeners(new Events());
        MsgManager.init(new Configuration(this.getDataFolder(), "Messages"));
        EMenu.loadMenus();
        Vault.setupEconomy();
        StatusManager.loadStatuses();
        this.createPlaceholder("lunaclans", ((player, params) -> {
            if (player == null) return "Игрок не найден";
            switch (params) {
                case "shopTimer" -> {
                    return toText(getNext());
                } case "tag" -> {
                    if (!ClansManager.hasClan(player)) return MsgManager.getMessage("noClanPlaceholder");
                    return ClansManager.getClan(player).getTag();
                } case "prefix" -> {
                    if (!ClansManager.hasClan(player)) return MsgManager.getMessage("noClanPlaceholder");
                    return ClansManager.getClan(player).getPrefix();
                } case "balance" -> {
                    if (!ClansManager.hasClan(player)) return MsgManager.getMessage("noClanPlaceholder");
                    return String.valueOf(ClansManager.getClan(player).getBalance());
                } case "level" -> {
                    if (!ClansManager.hasClan(player)) return MsgManager.getMessage("noClanPlaceholder");
                    return String.valueOf(ClansManager.getClan(player).getLevel().level());
                } case "storageCapacity" -> {
                    if (!ClansManager.hasClan(player)) return MsgManager.getMessage("noClanPlaceholder");
                    return String.valueOf(ClansManager.getClan(player).getLevel().storageCapacity());
                } default -> { return "Неверный идентификатор!"; }
            }
        }));
    }
    public static LocalDateTime getNext() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date;
        ListIterator<String> listIterator = EMenu.ShopMenu.getConfiguration().getStringList("shop.times").listIterator();
        while (listIterator.hasNext()) {
            String strDate = listIterator.next();
            date = LocalDateTime.of(now.toLocalDate(), LocalTime.parse(strDate));
            if (date.isAfter(now)) {
                date = LocalDateTime.of(now.toLocalDate(), LocalTime.parse(listIterator.previous()));
                return date;
            }
        }
        return null;
    }
    public static String toText(LocalDateTime time) {
        String hoursStr, minutesStr, secondsStr;
        LocalDateTime now = LocalDateTime.now();
        long diff = ChronoUnit.MILLIS.between(now, time);

        int hours = (int) diff / (1000 * 60 * 60);
        int minutes = (int) ((diff - (hours * 3600000)) / 60000);
        int seconds = (int) (diff - (hours * (1000 * 60 * 60)) - (minutes * (1000 * 60))) / 1000;

        if (hours < 10) hoursStr = "0" + hours;
        else hoursStr = String.valueOf(hours);

        if (minutes < 10) minutesStr = "0" + minutes;
        else minutesStr = String.valueOf(minutes);

        if (seconds < 10) secondsStr = "0" + seconds;
        else secondsStr = String.valueOf(seconds);

        return EMenu.ShopMenu.getConfiguration().getString("shop.timeFormat")
                .replace("hh", hoursStr)
                .replace("mm", minutesStr)
                .replace("ss", secondsStr);
    }
    @Override
    public void onDisable() {
        ClansManager.serializeClans();
    }
}
