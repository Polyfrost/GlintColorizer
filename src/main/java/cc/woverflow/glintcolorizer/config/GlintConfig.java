//#if MODERN==0 || FABRIC==1
package cc.woverflow.glintcolorizer.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator;
import cc.woverflow.glintcolorizer.GlintColorizer;
import net.minecraft.client.Minecraft;

import java.io.File;

public class GlintConfig extends Config {

    @Color(
            name = "Enchantment Glint Color",
            category = "General"
    )
    public static OneColor color = new OneColor(-8372020);

    @Switch(
            name = "Shiny Potions",
            category = "General"
    )
    public static boolean potionGlint;

    @Button(
            name = "Reset Color",
            category = "General", text = "Reset"
    )
    Runnable reset = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        color = new OneColor(-8372020);
        GlintColorizer.config.save();
        GlintColorizer.config.openGui();
    });

    public GlintConfig() {
        super(new Mod(GlintColorizer.NAME, ModType.UTIL_QOL, new VigilanceMigrator(new File(GlintColorizer.modDir, GlintColorizer.ID + ".toml").getPath())), GlintColorizer.ID + ".json");
        initialize();
    }
}
//#endif