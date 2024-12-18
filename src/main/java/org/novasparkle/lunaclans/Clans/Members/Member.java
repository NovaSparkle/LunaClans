package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Member {
    @Getter
    private final String nickName;

    @Setter
    @Getter
    private Status status;

    @Getter
    private final Player player;

    private final String killsPlaceholder;

    private final String playerTimeTotalPlaceholder;

    private final String lastLoginDatePlaceholder;

    public Member(String nickName, Status status) {
        this.nickName = nickName;
        this.status = status;
        this.player = Bukkit.getPlayerExact(this.nickName);
//        this.moneyPlaceholder = "%parseother_{" + this.nickName + "}_{vault_eco_balance}";
        this.killsPlaceholder = "%parseother_{" + this.nickName + "}_{statistic_player_kills}";
        this.lastLoginDatePlaceholder = "%parseother_{" + this.nickName + "}_{statistic_player_kills}";
        this.playerTimeTotalPlaceholder = "%parseother_{" + this.nickName + "}_{statistic_last_join}";
    }
    public void sendMessage(String message) {
        this.player.sendMessage(message);
    }
}
