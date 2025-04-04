package org.novasparkle.lunaclans.Clans.ClanComponents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.novasparkle.lunaspring.API.Configuration.Configuration;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class AComponent<T> {
    @Setter
    private int capacity;
    private final Configuration configuration;

    public abstract List<T> getItems();
}
