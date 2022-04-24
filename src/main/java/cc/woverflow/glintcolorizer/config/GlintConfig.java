//#if MODERN==0 || FABRIC==1
package cc.woverflow.glintcolorizer.config;

import cc.woverflow.glintcolorizer.GlintColorizer;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.awt.*;
import java.io.File;

public class GlintConfig extends Vigilant {

    @Property(
            type = PropertyType.SWITCH,
            name = "Enable Chroma",
            description = "Use a chroma color instead of a static color.\nThis overwrites the other color setting.",
            category = "General"
    )
    public static boolean chroma;

    @Property(
            type = PropertyType.COLOR,
            name = "Color",
            description = "Change the color of the enchantment glint.",
            category = "General"
    )
    public static Color color = new Color(-8372020);

    public GlintConfig() {
        super(new File(GlintColorizer.modDir, GlintColorizer.ID + ".toml"), GlintColorizer.NAME);
        initialize();
    }
}
//#endif