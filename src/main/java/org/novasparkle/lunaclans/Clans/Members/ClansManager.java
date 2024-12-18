package org.novasparkle.lunaclans.Clans.Members;

import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Clan;

import java.util.ArrayList;
import java.util.List;

public class ClansManager {
    private static final List<Clan> clans = new ArrayList<>();
    public static void addClan(Clan clan) {
        clans.add(clan);
    }
    public static void deleteClan(Clan clan) {
        clans.remove(clan);
    }
    public static Clan getClan(Player member) {
        return clans.stream().filter(clan -> clan.getPlayerStructure().containsMember(member)).findFirst().orElse(null);
    }
    public static Clan getClan(String tag) {
        return clans.stream().filter(clan -> clan.getTag().equals(tag)).findFirst().orElse(null);
    }
    public static boolean hasClan(Player member) {
        return clans.stream().anyMatch(clan -> clan.getPlayerStructure().containsMember(member) || clan.getPlayerStructure().getLeader().getPlayer().equals(member));
    }
}
