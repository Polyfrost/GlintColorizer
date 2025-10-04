package org.polyfrost.glintcolorizer

//#if FABRIC
//$$ import net.fabricmc.api.ClientModInitializer;
//#elseif FORGE
//#if MC >= 1.16.5
//$$ import net.minecraftforge.eventbus.api.IEventBus;
//$$ import net.minecraftforge.fml.common.Mod;
//$$ import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//#else
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif
//#endif

import com.mojang.brigadier.Command
import dev.deftu.omnicore.api.identifierOrThrow
import net.minecraft.util.ResourceLocation
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.utils.v1.dsl.openUI

//#if FORGE-LIKE
//#if MC >= 1.16.5
//$$ @Mod(GlintColorizerConstants.ID)
//#else
//$$ @Mod(modid = GlintColorizerConstants.ID, version = GlintColorizerConstants.VERSION)
//#endif
//#endif
object GlintColorizer
//#if FABRIC
//$$ : ClientModInitializer
//#endif
{
    fun id(path: String): ResourceLocation {
        return identifierOrThrow(GlintColorizerConstants.ID, path)
    }

    //#if FORGE-LIKE
    @Mod.EventHandler
    //#else
    //$$ override
    //#endif
    fun onInitializeClient(
        //#if FORGE-LIKE
        event: FMLInitializationEvent?
        //#endif
    ) {
        GlintConfig
        CommandManager.register(CommandManager.literal("glintcolorizer").executes {
            GlintConfig.openUI()
            Command.SINGLE_SUCCESS
        })
    }

    //#if FORGE && MC >= 1.16.5
    //$$ init {
    //$$     setupForgeEvents(FMLJavaModLoadingContext.get().modEventBus)
    //$$ }
    //#endif

    //#if FORGE-LIKE && MC >= 1.16.5
    //$$ private fun setupForgeEvents(modEventBus: IEventBus) {
    //$$     modEventBus.addListener(this::onInitializeClient)
    //$$ }
    //#endif
}
