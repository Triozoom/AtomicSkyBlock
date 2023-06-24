package com.atom.skyblock.papi;

import com.atom.skyblock.SBMain;
import com.atom.skyblock.configuration.SBConfig;
import dev.joel.bukkitutil.BukkitUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PAPI extends PlaceholderExpansion {

    @Override
    public boolean register() {
        BukkitUtil.send("§9[ASB] §bPlaceholderAPI found, registering placeholders...");
        return super.register();
    }

    @Override
    public String getIdentifier() {
        return "asb";
    }

    @Override
    public String getAuthor() {
        return "AtomDev";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("blocks")) {
            return SBMain.totalGlobalCobblestoneBroken + "";
        }else if (params.equalsIgnoreCase("phase")) {
            return SBMain.phase + "";
        }else if (params.equalsIgnoreCase("remaining")) {
            switch (SBMain.phase) {
                case 1:
                    return (SBConfig.blocksBrokenPhase2 - SBMain.totalGlobalCobblestoneBroken) + "";
                case 2:
                    return (SBConfig.blocksBrokenPhase3 - SBMain.totalGlobalCobblestoneBroken) + "";
                case 3:
                    return (SBConfig.blocksBrokenPhase4 - SBMain.totalGlobalCobblestoneBroken) + "";
                case 4:
                    return (SBConfig.blocksBrokenPhase5 - SBMain.totalGlobalCobblestoneBroken) + "";
                case 5:
                    return (SBConfig.blocksBrokenPhase6 - SBMain.totalGlobalCobblestoneBroken) + "";
                case 6:
                    return "Você já terminou o jogo!";
            }
        }
        return null;
    }
}
