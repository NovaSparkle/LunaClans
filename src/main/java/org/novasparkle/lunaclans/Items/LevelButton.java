package org.novasparkle.lunaclans.Items;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.ClanComponent;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Configurations.ConfigManager;
import org.novasparkle.lunaclans.Configurations.MsgManager;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaclans.Menus.LevelUp;
import org.novasparkle.lunaclans.Utils.Vault;
import org.novasparkle.lunaspring.API.Menus.Items.Item;
import org.novasparkle.lunaspring.API.Menus.MenuManager;
import org.novasparkle.lunaspring.API.Util.utilities.LunaMath;

import java.util.List;

public class LevelButton extends Item {

    @Getter
    public enum LevelType {
        OPENED,
        NEXT,
        CLOSED;
        private final Material material;
        private final boolean enchanted;
        public final String localName;

        LevelType() {
            this.material = ConfigManager.getMaterial(String.format("levelTypes.%s.material", this.name()));
            this.enchanted = ConfigManager.getConfig().getBoolean(String.format("levelTypes.%s.enchanted", this.name()));
            this.localName = ConfigManager.getString(String.format("levelTypes.%s.localName", this.name()));
        }
    }


    public record Level(int level,
                        int cost,
                        int playersLimit,
                        int bankCapacity,
                        int storageCapacity,
                        int playerStorageCapacity,
                        int shopLimit,
                        int spawnerLimit,
                        String newOption) {
        public Level(ConfigurationSection section) {

            this(LunaMath.toInt(section.getName()),
                    section.getInt("cost"),
                    section.getInt("playersLimit"),
                    section.getInt("bankCapacity"),
                    section.getInt("storageCapacity"),
                    section.getInt("playerStorageCapacity"),
                    section.getInt("shopLimit"),
                    section.getInt("spawnerLimit"),
                    section.getString("newOption"));
        }
    }

    private final LevelType levelType;
    private final Level level;
    private final Menu fromMenu;
    private ClanComponent clanComponent;
    public LevelButton(ConfigurationSection section, LevelType levelType, Menu fromMenu) {
        super(levelType.material, section.getInt("amount"));
        this.levelType = levelType;
        this.fromMenu = fromMenu;

        this.level = new Level(section);

        this.setDisplayName(section.getString("displayName"));
        this.setSlot((byte) LunaMath.getIndex(section.getInt("slot.row"), section.getInt("slot.column")));

        if (levelType.isEnchanted()) this.setGlowing(true);

        List<String> lore = section.getStringList("lore");
        lore.replaceAll(l -> l.replace("[levelStatus]", levelType.getLocalName())
                .replace("[cost]", String.valueOf(this.level.cost))
                .replace("[playersLimit]", String.valueOf(this.level.playersLimit))
                .replace("[bankCapacity]", String.valueOf(this.level.bankCapacity))
                .replace("[storageCapacity]", String.valueOf(this.level.storageCapacity))
                .replace("[playerStorageCapacity]", String.valueOf(this.level.playerStorageCapacity))
                .replace("[shopLimit]", String.valueOf(this.level.shopLimit))
                .replace("[spawnerLimit]", String.valueOf(this.level.spawnerLimit)));
        if (section.contains("newOption")) {
            this.clanComponent =    ClanComponent.valueOf(this.level.newOption);
            lore.replaceAll(l -> l.replace("[newOption]", clanComponent.getTranslation()));

        }
        this.setLore(lore);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        switch (this.levelType) {
            case OPENED -> player.sendMessage(MsgManager.getMessage("levels.openedLevel"));
            case NEXT -> {
                if (Vault.getEconomy().getBalance(player) >= this.level.cost) {
                    Clan clan = ClansManager.getClan(player);
                    clan.nextLevel();

                    player.sendMessage(MsgManager.getMessage("levels.nextLevelUnlocked"));
                    Vault.getEconomy().withdrawPlayer(player, this.level.cost);
                    MenuManager.openInventory(player, new LevelUp(player, EMenu.LevelUp, this.fromMenu));
                } else {
                    player.sendMessage(MsgManager.getMessage("bank.NotEnoughMoneyPlayer"));
                }
            }
            case CLOSED -> player.sendMessage(MsgManager.getMessage("levels.closedLevel"));
        }
    }
}
