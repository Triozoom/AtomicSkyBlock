package com.atom.skyblock.ultilidadesfodas;

import com.atom.skyblock.SBMain;
import dev.atom.atomachievements.api.AchievementAPI;
import dev.joel.bukkitutil.BukkitUtil;
import java.io.IOException;

public class AtomAchievementsHook {

    private static void register() {
        final String packageName = "com.atom.skyblock.achievements";
        try {
            AchievementAPI.registerAllAchievementsFromPackage(packageName, SBMain.INSTANCE);
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean start() {
        if (BukkitUtil.INSTANCE.isAPlugin("AtomAchievements")) {
            BukkitUtil.send("§aASB is starting the hooking with AtomAchievements.");
            register();
            return true;
        }
        return false;
    }

}
