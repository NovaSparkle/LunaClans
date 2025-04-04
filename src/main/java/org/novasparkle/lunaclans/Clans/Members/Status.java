package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class Status {
    private final String tag;
    private final String prefix;
    private final int priority;
    Status(String tag, ConfigurationSection section) {
        this.tag = tag;
        this.priority = section.getInt("priority");
        this.prefix = section.getString("prefix");
    }
    public boolean isLower(Status status) {
        return this.getPriority() < status.getPriority();
    }

    public Status next() {
        if (this.priority > StatusManager.getStats().size() - 1) throw new IndexOutOfBoundsException(this.priority);
        return StatusManager.getStats().stream().filter(p -> p.priority == this.priority + 1).findFirst().orElse(null);
    }
    public Status previous() {
        if (this.priority < 0) throw new IndexOutOfBoundsException(this.priority);
        return StatusManager.getStats().stream().filter(p -> p.priority == this.priority - 1).findFirst().orElse(null);
    }
}
