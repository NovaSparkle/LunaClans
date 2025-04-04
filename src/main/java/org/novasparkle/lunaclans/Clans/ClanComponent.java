package org.novasparkle.lunaclans.Clans;

import lombok.Getter;
import org.novasparkle.lunaclans.Configurations.ConfigManager;

@Getter
public enum ClanComponent {
    STORAGE(),
    PLAYER_STORAGE(),
    SHOP(),
    LINK();
    private final String translation;

    ClanComponent() {
        this.translation = ConfigManager.getString(String.format("ClanComponents.%s", this.name()));
    }
}
