package org.novasparkle.lunaclans.Clans.JSON;

import lombok.Getter;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.Members.Member;
import org.novasparkle.lunaclans.Clans.Members.PlayerStructure;
import org.novasparkle.lunaclans.Clans.Members.Status;
import org.novasparkle.lunaclans.Configurations.StorageManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


@Getter
public class JManager {

    private static final JSONParser parser = new JSONParser();

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static File createNewClan(String tag, String prefix, int level,
                                     int maxPlayers, PlayerStructure structure) {
        File file = new File(StorageManager.getClansStorage(), tag + ".json");
        if (!file.createNewFile()) {
            throw new RuntimeException(String.format("Не удалось создать файл клана %s!", tag));
        }
        FileWriter writer = new FileWriter(file);

        JSONObject container = new JSONObject();
        JSONObject playerStructure = new JSONObject();

        for (Status status : Status.values()) {
            JSONArray array = new JSONArray();

            structure.getMembers()
                    .stream()
                    .map(Member::getNickName)
                    .forEach(array::add);

            playerStructure.put(status.name().toLowerCase(), array);
        }

        container.put("tag", tag);
        container.put("prefix", prefix);
        container.put("level", level);
        container.put("maxPlayers", maxPlayers);
        container.put("playerStructure", playerStructure);

        writer.write(container.toJSONString());
        writer.flush();
        writer.close();

        return file;
    }

    @SneakyThrows
    public static Clan deserializeClan(File clanFile) {
        JSONObject fileContainer = (JSONObject) parser.parse(new FileReader(clanFile));
        if (fileContainer.isEmpty()) return null;
        JSONObject playerStructure = (JSONObject) fileContainer.get("playerStructure");
        List<Member> members = new ArrayList<>();
        List<Status> s = new ArrayList<>(List.of(Status.values()));
        for (Status category : s) {
            JSONArray array = (JSONArray) playerStructure.get(category.name().toLowerCase());
            for (Object object : array) {
                String nick = (String) object;
                Member member = new Member(nick, category);
                members.add(member);
            }
        }
        Clan clan = new Clan(getString(fileContainer, "tag"),
                        getString(fileContainer, "prefix"),
                        new PlayerStructure(new Member(getString(playerStructure, "leader"), Status.LEADER), members),
                        Integer.parseInt(getString(fileContainer, "level")),
                        clanFile);

        clan.setMaxPlayers((Byte) fileContainer.get("max_players"));
        return clan;
    }
    private static String getString(JSONObject container, String  key) {
        return (String) container.get(key);
    }


}