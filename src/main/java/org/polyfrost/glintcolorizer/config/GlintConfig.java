//#if MODERN==0 || FABRIC==1
package org.polyfrost.glintcolorizer.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator;
import org.polyfrost.glintcolorizer.GlintColorizer;
import net.minecraft.client.Minecraft;

import java.io.File;

public class GlintConfig extends Config {

    @Color(
            name = "Enchantment Glint Color"
    )
    public static OneColor color = new OneColor(-8372020);

    @Switch(
            name = "Shiny Potions"
    )
    public static boolean potionGlint;

    @Dropdown(
            name = "Potion Glint Type",
            options = {"Background Only", "All"}
    )
    public static int potionGlintType = 0;

    @Button(
            name = "Reset Color",
            text = "Reset"
    )
    Runnable reset = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        color = new OneColor(-8372020);
        GlintColorizer.config.save();
        GlintColorizer.config.openGui();
    });

    @Exclude private static final File oldModDir = new File(new File("./W-OVERFLOW"), GlintColorizer.NAME);

    public GlintConfig() {
        super(new Mod(GlintColorizer.NAME, ModType.UTIL_QOL, "/glintcolorizer_dark.svg", new VigilanceMigrator(new File(oldModDir, GlintColorizer.ID + ".toml").getPath())), GlintColorizer.ID + ".json");
        initialize();

        addDependency("potionGlintType", "potionGlint");
    }
}
//#endif