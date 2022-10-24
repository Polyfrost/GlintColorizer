//#if MODERN==0 || FABRIC==1
package cc.woverflow.glintcolorizer;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.woverflow.glintcolorizer.config.GlintConfig;
import java.io.File;

//#if MODERN==0
import net.minecraftforge.fml.common.Mod;
import cc.woverflow.glintcolorizer.command.GlintCommand;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//#if MODERN==0
@Mod(modid = GlintColorizer.ID, name = GlintColorizer.NAME, version = GlintColorizer.VER)
//#endif
public class GlintColorizer {
    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";
    public static File modDir = new File(new File("./W-OVERFLOW"), NAME);
    public static GlintConfig config;

    //#if MODERN==0
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    //#else
    //$$ public void onPreInit()
    //#endif
    {
        if (!modDir.exists()) modDir.mkdirs();
    }

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