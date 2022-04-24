//#if MODERN==0 || FABRIC==1
package com.example.template;

import cc.woverflow.onecore.utils.Updater;
import com.example.template.config.TemplateConfig;
import java.io.File;

//#if MODERN==0
import net.minecraftforge.fml.common.Mod;
import com.example.template.command.TemplateCommand;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//#else
//$$ import cc.woverflow.onecore.utils.Utils;
//#endif

//#if MODERN==0
@Mod(modid = ModTemplate.ID, name = ModTemplate.NAME, version = ModTemplate.VER)
//#endif
public class ModTemplate {
    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";
    public static File modDir = new File(new File("./W-OVERFLOW"), NAME);
    public static TemplateConfig config;

    //#if MODERN==0
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    //#else
    //$$ public void onPreInit()
    //#endif
    {
        if (!modDir.exists()) modDir.mkdirs();
        //#if FABRIC==1
        //$$ File file = Utils.getFileOfMod(ID);
        //$$ if (file == null) return;
        //#endif
        Updater.INSTANCE.addToUpdater(
                //#if FABRIC==0
                event.getSourceFile()
                //#else
                //$$ file
                //#endif
                , NAME, ID, VER, "W-OVERFLOW/" + ID);
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
        new TemplateCommand().register();
        //#endif
        config = new TemplateConfig();
        config.preload();
    }
}
//#endif