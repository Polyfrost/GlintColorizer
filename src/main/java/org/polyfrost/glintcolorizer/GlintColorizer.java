//#if MODERN==0 || FABRIC==1
package org.polyfrost.glintcolorizer;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import org.polyfrost.glintcolorizer.config.GlintConfig;

//#if MODERN==0
import net.minecraftforge.fml.common.Mod;
import org.polyfrost.glintcolorizer.command.GlintCommand;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//#endif

//#if MODERN==0
@Mod(modid = GlintColorizer.ID, name = GlintColorizer.NAME, version = GlintColorizer.VER)
//#endif
public class GlintColorizer {
    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";
    public static GlintConfig config;

    //#if MODERN==0
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event)
    //#else
    //$$ public void onInit()
    //#endif
    {
        //#if MODERN==1
        //$$ onPreInit();
        //#endif
        //#if MODERN==0
        CommandManager.INSTANCE.registerCommand(new GlintCommand());
        //#endif
        config = new GlintConfig();
    }
}
//#endif