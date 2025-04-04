package org.novasparkle.lunaclans.Clans;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanShop;
import org.novasparkle.lunaclans.Clans.ClanComponents.ClanStorage;
import org.novasparkle.lunaclans.Clans.Members.PlayerStructure;
import org.novasparkle.lunaclans.Configurations.StorageManager;
import org.novasparkle.lunaclans.Items.LevelButton;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaspring.API.Configuration.Configuration;
import org.novasparkle.lunaspring.API.Util.utilities.Utils;

@Getter
public class Clan {

    public Clan(String tag, String prefix, Player leader) {
        this.prefix = Utils.color(prefix);
        this.tag = tag;
        this.structure = new PlayerStructure(leader);
        Configuration configuration = EMenu.LevelUp.getConfiguration();
        this.level = new LevelButton.Level(configuration.getSection(String.format("levels.%d", configuration.getInt("firstLevel"))));
        this.balance = 0;
        this.config = new Configuration(StorageManager.getClansStorage(), tag);
        this.storage = null;
    }

    public Clan(String tag, String prefix, PlayerStructure structure, int level, int balance, Configuration config) {
        this.prefix = Utils.color(prefix);
        this.tag = tag;
        this.structure = structure;
        this.level = new LevelButton.Level(EMenu.LevelUp.getConfiguration().getSection(String.format("levels.%d", level)));
        this.config = config;
        this.balance = balance;
    }

    @Setter private String tag;
    @Setter private String prefix;

    private final PlayerStructure structure;

    @Setter
    private LevelButton.Level level;
    private int balance;

    private final Configuration config;

    @Setter
    private ClanStorage storage;

    @Setter
    @Getter
    private ClanShop shop;

    public void nextLevel() {
        this.setLevel(new LevelButton.Level(EMenu.LevelUp.getConfiguration().getSection(String.format("levels.%d", this.level.level() + 1))));
        this.updateClanStorage(this.getLevel());
        this.updateClanShop(this.getLevel());
        this.config.set("level", this.getLevel().level());
    }
    private void updateClanStorage(LevelButton.Level level) {
        if (level.storageCapacity() > 0) {
            if (this.getStorage() != null) {
                this.getStorage().setCapacity(level.storageCapacity());
            } else
                this.setStorage(new ClanStorage(level.storageCapacity(), this.getConfig()));
        }
    }
    private void updateClanShop(LevelButton.Level level) {
        if (level.shopLimit() > 0) {
            if (this.shop != null) {
                // Обновляем вместительность
                this.getShop().setCapacity(level.shopLimit());
            } else
                // Новый, пустой магазин, если перешли на уровень с ним
                this.setShop(new ClanShop(level.shopLimit(), this.getConfig()));
        }
    }
    public void setBalance(int balance) {
        this.balance = balance;
        this.config.set("balance", balance);
    }
}
