package org.novasparkle.lunaclans.Clans;

import lombok.Getter;
import lombok.Setter;
import org.novasparkle.lunaclans.Clans.Members.PlayerStructure;

@Getter
public class Clan {
    @Setter
    private String tag;
    @Setter
    private String prefix;
    private PlayerStructure playerStructure;
}
