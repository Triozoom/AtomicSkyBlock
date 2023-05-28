package com.atom.skyblock.configuration;

import dev.joel.bukkitutil.config.ConfigSetting;

public class SBConfig {

    @ConfigSetting(path = "quality-of-life", name = "friendly-fire")
    public static boolean ff = false;

    @ConfigSetting(path = "phases.2", name = "block-requirement")
    public static int blocksBrokenPhase2 = 1250;

    @ConfigSetting(path = "phases.3", name = "block-requirement")
    public static int blocksBrokenPhase3 = 2785;

    @ConfigSetting(path = "phases.4", name = "block-requirement")
    public static int blocksBrokenPhase4 = 4150;

    @ConfigSetting(path = "phases.5", name = "block-requirement")
    public static int blocksBrokenPhase5 = 6520;

    @ConfigSetting(path = "phases.6", name = "block-requirement")
    public static int blocksBrokenPhase6 = 11520;

}
