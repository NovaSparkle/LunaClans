package org.novasparkle.lunaclans.Clans;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.JSON.JManager;
import org.novasparkle.lunaclans.Clans.Members.PlayerStructure;
import org.novasparkle.lunaspring.Util.Utils;

import java.io.File;

@Getter
public class Clan {
    public Clan(String prefix, String tag, Player leader) {
        this.prefix = Utils.color(prefix);
        this.tag = tag;
        this.structure = new PlayerStructure(leader);
        this.level = 1;

        this.file = JManager.createNewClan(this.tag, this.prefix, this.level, this.maxPlayers, this.structure);
    }
    public Clan(String prefix, String tag, PlayerStructure structure, int level, File file) {
        this.prefix = Utils.color(prefix);
        this.tag = tag;
        this.structure = structure;
        this.level = level;
        this.file = file;
    }
    @Setter
    private String tag;
    @Setter
    private String prefix;
    private final PlayerStructure structure;
    @Setter
    private int level;
    @Setter
    private int maxPlayers;
    private final File file;
}
