package org.novasparkle.lunaclans.Clans.ClanComponents;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Items.ShopItem;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaspring.API.Configuration.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class ClanShop extends AComponent<ShopItem> {
    private final int updateDelay;
    @Setter
    private List<ShopItem> items;
    public ClanShop(int capacity, Configuration config) {
        super(capacity, config);
        this.items = new ArrayList<>();
        this.updateDelay = ConfigManager.getInt("shop.updateDelay");
        this.deserialize();
        ShopUpdater updater = new ShopUpdater();
    }

    public void update() {
        List<ShopItem> itemList = new ArrayList<>();

        Configuration configuration = EMenu.ShopMenu.getConfiguration();
        ConfigurationSection section = configuration.getSection("shop.items");

        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            itemList.add(new ShopItem(itemSection));
        }
        if (itemList.isEmpty()) throw new RuntimeException("Список товаров магазина пуст!");

        Collections.shuffle(itemList);

        this.items.addAll(itemList);
    }
    public void deserialize() {
        ConfigurationSection section = this.getConfiguration().getSection("shop.items");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                ConfigurationSection itemSection = section.getConfigurationSection(key);
                ShopItem item = new ShopItem(itemSection);
                assert itemSection != null;
                item.setBought(itemSection.getInt("bought"));
                this.items.add(item);
            }
        } else {
            this.update();
        }
    }
    public void serialize() {
        this.getConfiguration().set("shop.items", null);
        Iterator<ShopItem> iter = this.items.iterator();
        int counter = 1;
        while (iter.hasNext()) {
            ShopItem item = iter.next();
            item.serialize(this.getConfiguration().createSection("shop.items", String.valueOf(counter++)));
        }
        this.getConfiguration().save();
    }

    public class ShopUpdater extends BukkitRunnable {
        @Override
        public void run() {
            ClanShop.this.update();
        }
    }
}
