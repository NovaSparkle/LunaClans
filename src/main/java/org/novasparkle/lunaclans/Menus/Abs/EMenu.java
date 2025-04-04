package org.novasparkle.lunaclans.Menus.Abs;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.novasparkle.lunaclans.Configurations.StorageManager;
import org.novasparkle.lunaclans.LunaClans;
import org.novasparkle.lunaspring.API.Configuration.Configuration;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

@Getter
public enum EMenu {

    MembersList("AllClanMembersMenu"),
    AllPlayersList("AllPlayersListMenu"),
    Bank("ClanBankMenu"),
    Create("ClanCreateMenu"),
    Invite("ClanInviteMenu"),
    Leave("ClanLeaveMenu"),
    LevelUp("ClanLevelUpMenu"),
    ClansList("ClanListMenu"),
    MainMenu("MainMenu"),
    Remove("ClanRemoveMenu"),
    SelfStorageMenu("ClanSelfStorageMenu"),
    ShopMenu("ClanShopMenu"),
    StorageMenu("ClanStorageMenu"),
    MemberEdit("MemberEditMenu"),
    LinkMenu("LinkMenu");

    private Configuration configuration;
    private final String fileName;
    EMenu(String fileName) {
        this.fileName = fileName;
    }
    public static EMenu getByFileName(String name) {
        return Arrays.stream(values()).filter(menus -> menus.getConfiguration().getFile().getName().replace(".yml", "").equals(name)).findFirst().orElse(null);
    }
    @SneakyThrows
    public static void loadMenus() {
        LunaClans INSTANCE = LunaClans.getINSTANCE();
        for (EMenu m : values()) {
            File file = new File(StorageManager.getMenusStorage(), m.fileName + ".yml");
            INSTANCE.info(String.format("Загрузка меню %s", file.getName()));

            if (!file.exists() && file.createNewFile()) {
                InputStream in = INSTANCE.getResource("Menus/" + file.getName());
                assert in != null;
                FileUtils.copyInputStreamToFile(in, file);
            }

            m.configuration = new Configuration(StorageManager.getMenusStorage(), m.fileName);
        }
    }
}
