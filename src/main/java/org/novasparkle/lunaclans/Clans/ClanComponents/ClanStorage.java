package org.novasparkle.lunaclans.Clans.ClanComponents;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaspring.API.Configuration.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ClanStorage extends AComponent<ItemStack> {
    private final List<ItemStack> items;
    private UUID viewer;
    public ClanStorage(int capacity, Configuration configuration) {
        super(capacity, configuration);
        this.items = new ArrayList<>();
        ConfigurationSection section = this.getConfiguration().getSection("StorageItems");
        if (section == null) {
            return;
        }
        if (this.getItems().isEmpty()) {
            List<ItemStack> itemStackList = new ArrayList<>();
            for (String key : section.getKeys(false)) {
                itemStackList.add(section.getItemStack(key));
            }
            this.setItems(itemStackList, "StorageItems");
        }
    }

    public ClanStorage(int capacity, List<ItemStack> items, Configuration configuration) {
        super(capacity, configuration);
        this.items = items;
    }
    public void setItems(List<ItemStack> items, String path) {
        this.items.clear();
        int counter = 1;
        this.getConfiguration().set(path, null);
        for (ItemStack stack : items) {
            this.items.add(stack);
            this.getConfiguration().setItemStack(String.format("%s.%d", path, counter++), stack);
        }
        this.getConfiguration().save();
    }
    public void open(UUID uuid) {
        this.viewer = uuid;
    }
    public void close() {
        this.viewer = null;
    }

    public boolean isViewed() {
        return this.viewer != null;
    }
}
