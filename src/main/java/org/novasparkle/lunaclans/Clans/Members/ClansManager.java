package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanShop;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanStorage;
import org.novasparkle.lunaclans.Configurations.StorageManager;
import org.novasparkle.lunaspring.API.Configuration.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClansManager {
    @Getter
    private static List<Clan> clans;
    static {
        loadClans();
    }
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
    public static boolean hasClan(OfflinePlayer player) {
        return clans.stream().anyMatch(clan -> clan.getStructure().containsMember(player) || clan.getStructure().getLeader().getPlayer().equals(player));
    }
    @SneakyThrows
    public static void loadClans() {
        try (Stream<Path> pathes = Files.walk(StorageManager.getClansStorage().toPath())) {
            clans = pathes.filter(Files::isRegularFile).map(path -> ClansManager.deserialize(path.toFile())).collect(Collectors.toList());
        }
    }
    @SneakyThrows
    public static void serializeClans() {
        clans.forEach(ClansManager::serialize);
    }
    public static void createClan(Player leader, String tag, String prefix) {
        addClan(new Clan(prefix, tag, leader));
    }
    public static void serialize(Clan clan) {
        Configuration configuration = clan.getConfig();

        configuration.set("tag", clan.getTag());
        configuration.set("prefix", clan.getPrefix());
        configuration.set("balance", clan.getBalance());
        configuration.set("level", clan.getLevel().level());
        configuration.set("tag", clan.getTag());
        ConfigurationSection section = configuration.createSection(configuration.self(), "playerStructure");

        PlayerStructure structure = clan.getStructure();
        section.set("leader", structure.getLeader().getName());
        for (Status status : StatusManager.getStats()) {
            if (status.equals(StatusManager.getLeader())) continue;
            else {
                List<String> players = new ArrayList<>();
                structure.getMembers().stream()
                        .filter(p -> p.getStatus().equals(status))
                        .map(Member::getName)
                                .forEach(players::add);

                section.set(status.getTag(), players);
            }
        }
        ClanShop shop = clan.getShop();
        if (shop != null) shop.serialize();

        configuration.save();
    }

    @SuppressWarnings("deprecation")
    public static Clan deserialize(File file) {
        Configuration configuration = new Configuration(file);

        PlayerStructure structure = new PlayerStructure(Bukkit.getOfflinePlayer(configuration.getString("playerStructure.leader")));
        ConfigurationSection section = configuration.getSection("playerStructure");
        structure.addMember(section.getString("leader"), StatusManager.getLeader());

        for (String status : section.getKeys(false)) {
            if (!status.equalsIgnoreCase(StatusManager.getLeader().getTag())) {
                List<String> membersListSection = section.getStringList(status);
                membersListSection.forEach(nick -> structure.addMember(nick, StatusManager.getByTag(status)));
            }
        }

        Clan clan = new Clan(configuration.getString("tag"),
                configuration.getString("prefix"),
                structure,
                configuration.getInt("level"),
                configuration.getInt("balance"),
                configuration);

        if (clan.getLevel().storageCapacity() > 0) {
            clan.setStorage(new ClanStorage(clan.getLevel().storageCapacity(), configuration));
        }
        if (clan.getLevel().shopLimit() > 0) {
            clan.setShop(new ClanShop(clan.getLevel().shopLimit(), clan.getConfig()));
        }

        return clan;
    }
}
