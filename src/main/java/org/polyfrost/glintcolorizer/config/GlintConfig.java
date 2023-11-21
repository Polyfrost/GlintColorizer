//#if MODERN==0 || FABRIC==1
package org.polyfrost.glintcolorizer.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Switch;
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

    public GlintConfig() {
        super(new Mod(GlintColorizer.NAME, ModType.UTIL_QOL, "/glintcolorizer_dark.svg", new VigilanceMigrator(new File(GlintColorizer.modDir, GlintColorizer.ID + ".toml").getPath())), GlintColorizer.ID + ".json");
        initialize();

        addDependency("potionGlintType", "potionGlint");
    }
}
//#endif