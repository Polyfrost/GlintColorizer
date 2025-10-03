package org.polyfrost.glintcolorizer

//#if FABRIC
//$$import net.fabricmc.api.ClientModInitializer;
//#elseif FORGE
//#if MC >= 1.16.5
//$$import net.minecraftforge.eventbus.api.IEventBus;
//$$import net.minecraftforge.fml.common.Mod;
//$$import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//$$import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//$$import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//#else
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif
//#elseif NEOFORGE
//$$import net.neoforged.bus.api.IEventBus;
//$$import net.neoforged.fml.common.Mod;
//$$import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
//$$import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
//#endif

import com.mojang.brigadier.Command
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.utils.v1.dsl.openUI

//#if FORGE-LIKE
@Mod(
    modid = GlintColorizer.ID,
    name = GlintColorizer.NAME,
    version = GlintColorizer.VER,
    modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter"
)
//#endif
object GlintColorizer
//#if FABRIC
//$$: ClientModInitializer
//#endif
{
    const val NAME: String = "@MOD_NAME@"
    const val VER: String = "@MOD_VERSION@"
    const val ID: String = "@MOD_ID@"

//#if FORGE-LIKE
    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
//#elseif FABRIC
//$$override fun onInitializeClient() {
//#endif
        GlintConfig
        CommandManager.register(CommandManager.literal("glintcolorizer").executes {
            GlintConfig.openUI()
            Command.SINGLE_SUCCESS
        })
    }
}
