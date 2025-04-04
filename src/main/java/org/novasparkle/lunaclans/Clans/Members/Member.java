package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Objects;

@Getter
public class Member {
    private final String name;

    @Setter
    private Status status;

    private final OfflinePlayer player;


    public Member(String name, Status status) {
        this.name = name;
        this.status = status;
        this.player = Bukkit.getPlayerExact(this.name);
    }
    public void sendMessage(String message) {
        if (this.player.isOnline())
            Objects.requireNonNull(this.player.getPlayer()).sendMessage(message);
    }
}
