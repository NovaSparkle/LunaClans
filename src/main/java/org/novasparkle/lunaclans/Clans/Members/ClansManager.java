package org.novasparkle.lunaclans.Clans.Members;

import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.JSON.JManager;
import org.novasparkle.lunaclans.Configurations.StorageManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClansManager {
    private static final List<Clan> clans = getClans();
    public static void addClan(Clan clan) {
        clans.add(clan);
    }
    public static void deleteClan(Clan clan) {
        clans.remove(clan);
    }
    public static Clan getClan(OfflinePlayer player) {
        return clans.stream().filter(clan -> clan.getStructure().containsMember(player)).findFirst().orElse(null);
    }
    public static Clan getClan(String tag) {
        return clans.stream().filter(clan -> clan.getTag().equals(tag)).findFirst().orElse(null);
    }
    public static boolean hasClan(Player player) {
        return clans.stream().anyMatch(clan -> clan.getStructure().containsMember(player) || clan.getStructure().getLeader().getPlayer().equals(player));
    }
    @SneakyThrows
    private static List<Clan> getClans() {
        try (Stream<Path> pathes = Files.walk(StorageManager.getClansStorage().toPath())) {
            return pathes.filter(Files::isRegularFile).map(path -> JManager.deserializeClan(path.toFile())).collect(Collectors.toList());
        }
    }
    public static void createClan(Player leader, String tag, String prefix) {
        addClan(new Clan(prefix, tag, leader));
    }
}
