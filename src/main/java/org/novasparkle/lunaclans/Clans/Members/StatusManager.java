package org.novasparkle.lunaclans.Clans.Members;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;
import org.novasparkle.lunaclans.Configurations.ConfigManager;

import java.util.*;

public class StatusManager {
    @Getter
    private final static Set<Status> stats = new HashSet<>();
    public static void loadStatuses() {
        ConfigurationSection section = ConfigManager.getSection("Statuses");
        if (section == null) throw new RuntimeException("Секции статусов не существует, невозможно запустить плагин");
        section.getValues(false).forEach((tag, sSection) -> stats.add(new Status(tag, (ConfigurationSection) sSection)));
    }
    @Nullable
    public static Status getByTag(String tag) {
        return stats.stream().filter(s -> s.getTag().equals(tag)).findFirst().orElseThrow(NoSuchElementException::new);
    }
    public static Status getByPriority(int priority) {
        return stats.stream().filter(s -> s.getPriority() == priority).findFirst().orElseThrow(NoSuchElementException::new);
    }
    public static Status getLeader() {
        return stats.stream().max(Comparator.comparing(Status::getPriority)).orElseThrow(NoSuchElementException::new);
    }

}
