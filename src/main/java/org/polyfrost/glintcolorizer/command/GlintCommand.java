package org.polyfrost.glintcolorizer.command;

//#if MODERN==0

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import org.polyfrost.glintcolorizer.GlintColorizer;

@Command(value = "glintcolorizer", description = "Glint Colorizer commands")
public class GlintCommand {

    @Main
    public void handle() {
        GlintColorizer.config.openGui();
    }
}
//#endif