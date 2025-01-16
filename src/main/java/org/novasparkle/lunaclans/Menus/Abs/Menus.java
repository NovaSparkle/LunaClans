package org.novasparkle.lunaclans.Menus.Abs;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.novasparkle.lunaclans.Configurations.StorageManager;
import org.novasparkle.lunaclans.LunaClans;
import org.novasparkle.lunaspring.Configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Getter
public enum Menus {

    MembersList("AllClanMembersMenu.yml"),
    AllPlayersList("AllPlayersListMenu.yml"),
    Bank("ClanBankMenu.yml"),
    Create("ClanCreateMenu.yml"),
    Invite("ClanInviteMenu.yml"),
    Leave("ClanLeaveMenu.yml"),
    LevelUp("ClanLevelUpMenu.yml"),
    ClansList("ClanListMenu.yml"),
    MainMenu("MainMenu.yml"),
    Remove("ClanRemoveMenu.yml"),
    SelfStorage("ClanSelfStorageMenu.yml"),
    Shop("ClanShopMenu.yml"),
    Storage("ClanStorageMenu.yml"),
    MemberEdit("MemberEditMenu.yml");

    private final Configuration configuration;

    Menus(String fileName) {
        this.configuration = new Configuration(StorageManager.getMenusStorage(), fileName);
    }
    public static Menus getByFileName(String name) {
        return Arrays.stream(values()).filter(menus -> menus.getConfiguration().getFile().getName().replace(".yml", "").equals(name)).findFirst().orElse(null);
    }
    public static void downloadMenus() {
        LunaClans INSTANCE = LunaClans.getINSTANCE();
        for (Menus m : values()) {
            File file = m.getConfiguration().getFile();
            try {
                if (!file.exists() && file.createNewFile()) {
                    InputStream in = INSTANCE.getResource("Menus/" + file.getName());
                    assert in != null;
                    FileUtils.copyInputStreamToFile(in, file);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
