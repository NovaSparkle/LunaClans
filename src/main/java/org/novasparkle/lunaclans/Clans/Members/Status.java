package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import org.novasparkle.lunaclans.LunaClans;

import java.util.Arrays;

@Getter
public enum Status {
    MEMBER(1),
    MODERATOR(2),
    DEPUTY_LEADER(3),
    LEADER(4);
    private final String prefix;
    private final int priority;
    Status(int priority) {
        this.priority = priority;
        this.prefix = LunaClans.getINSTANCE().getConfig().getString("StatusPrefixes." + this.name());
    }
    public boolean isLower(Status status) {
        return this.getPriority() < status.getPriority();
    }
    public Status next() {
        if (this.priority > values().length - 1) return null;
        return Arrays.stream(values()).filter(p -> p.priority == this.priority + 1).findFirst().orElse(null);
    }
    public Status previous() {
        if (this.priority < 0) return null;
        return Arrays.stream(values()).filter(p -> p.priority == this.priority - 1).findFirst().orElse(null);
    }
}
