package org.novasparkle.lunaclans.Clans.ClanComponents;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Configurations.StorageManager;
import org.novasparkle.lunaspring.API.Configuration.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ClanSelfStorage extends ClanStorage {
    private final OfflinePlayer offlinePlayer;
    public ClanSelfStorage(int capacity, OfflinePlayer offlinePlayer) {
        super(capacity,
                new Configuration(
                        new File(StorageManager.getSelfStStorage(), ClansManager.getClan(offlinePlayer)
                                .getTag()), offlinePlayer.getName()));

        this.offlinePlayer = offlinePlayer;
        ConfigurationSection section = this.getConfiguration().getSection("StorageItems");
        if (section == null) return;

        if (this.getItems().isEmpty()) {
            List<ItemStack> itemStackList = new ArrayList<>();
            for (String key : section.getKeys(false)) {
                itemStackList.add(section.getItemStack(key));
            }
            this.setItems(itemStackList, "StorageItems");
        }
    }

    public ClanSelfStorage(int capacity, List<ItemStack> items, OfflinePlayer player) {
        super(capacity, items, new Configuration(StorageManager.getSelfStStorage(), player.getName()));
        this.offlinePlayer = player;
    }

}
