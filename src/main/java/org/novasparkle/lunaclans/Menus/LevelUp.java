package org.novasparkle.lunaclans.Menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.novasparkle.lunaclans.Clans.Clan;
import org.novasparkle.lunaclans.Clans.Members.ClansManager;
import org.novasparkle.lunaclans.Items.LevelButton;
import org.novasparkle.lunaclans.Menus.Abs.EMenu;
import org.novasparkle.lunaclans.Menus.Abs.Menu;
import org.novasparkle.lunaspring.API.Util.utilities.LunaMath;

public class LevelUp extends Menu {
    public LevelUp(Player player, EMenu eMenu, @Nullable Menu fromMenu) {
        super(player, eMenu, fromMenu);
        Clan clan = ClansManager.getClan(player);
        int level = clan.getLevel().level();

        ConfigurationSection section = this.getConfig().getSection("levels");
        for (String key : section.getKeys(false)) {
            ConfigurationSection levelSection = section.getConfigurationSection(key);
            assert levelSection != null;
            int sectionLevelInt = LunaMath.toInt(levelSection.getName());

            if (sectionLevelInt <= level) {
                this.addItems(new LevelButton(levelSection, LevelButton.LevelType.OPENED, this.getFromMenu()));
            } else if (sectionLevelInt == (level + 1)) {
                this.addItems(new LevelButton(levelSection, LevelButton.LevelType.NEXT, this.getFromMenu()));
            } else {
                this.addItems(new LevelButton(levelSection, LevelButton.LevelType.CLOSED, this.getFromMenu()));
            }
        }
    }
}
