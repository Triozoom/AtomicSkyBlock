package com.atom.skyblock.configuration;

import dev.joel.bukkitutil.config.ConfigSetting;

public class SBConfig {

    @ConfigSetting(path = "quality-of-life", name = "friendly-fire")
    public static boolean ff = false;

    @ConfigSetting(path = "quality-of-life", name = "keep-inventory-on-void-death")
    public static boolean kivd = false;

    @ConfigSetting(path = "phases.2", name = "block-requirement")
    public static int blocksBrokenPhase2 = 2500;

    @ConfigSetting(path = "phases.3", name = "block-requirement")
    public static int blocksBrokenPhase3 = 5570;

    @ConfigSetting(path = "phases.4", name = "block-requirement")
    public static int blocksBrokenPhase4 = 8300;

    @ConfigSetting(path = "phases.5", name = "block-requirement")
    public static int blocksBrokenPhase5 = 13040;

    @ConfigSetting(path = "phases.6", name = "block-requirement")
    public static int blocksBrokenPhase6 = 23040;

}
