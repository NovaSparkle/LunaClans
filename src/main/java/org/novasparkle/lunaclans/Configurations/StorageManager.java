package org.novasparkle.lunaclans.Configurations;

import org.novasparkle.lunaclans.LunaClans;

import java.io.File;

public class StorageManager {
    private final static File clansStorage;
    private final static File menusStorage;
    private final static File selfStStorage;

    static {
        menusStorage = createStorage("Menus");
        clansStorage = createStorage("Clans");
        selfStStorage = createStorage("PlayerStorages");
    }

    private static File createStorage(String fileName) {
        File file = new File(LunaClans.getINSTANCE().getDataFolder(), fileName);
        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new RuntimeException(String.format("Не удалось создать папку хранения: %s", fileName));
            }
        }
        return file;
    }

    public static File getClansStorage() {
        if (clansStorage != null) {
            return clansStorage;
        } else throw new RuntimeException("Папки хранения конфигураций кланов не существует, невозможно выполнить действие!");
    }
    public static File getSelfStStorage() {
        if (selfStStorage != null) {
            return selfStStorage;
        } else throw new RuntimeException("Папки хранения личных хранилищ не существует, невозможно выполнить действие!");
    }

    public static File getMenusStorage() {
        if (menusStorage != null) {
            return menusStorage;
        } else throw new RuntimeException("Папки хранения конфигураций меню не существует, невозможно выполнить действие!");
    }
}
