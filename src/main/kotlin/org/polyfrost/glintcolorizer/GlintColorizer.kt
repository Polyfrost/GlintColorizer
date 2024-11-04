package org.polyfrost.glintcolorizer

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import org.polyfrost.glintcolorizer.command.GlintCommand
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.oneconfig.api.commands.v1.CommandManager

@Mod(
    modid = GlintColorizer.ID,
    name = GlintColorizer.NAME,
    version = GlintColorizer.VER,
    modLanguageAdapter = "org.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object GlintColorizer {

    const val NAME: String = "@MOD_NAME@"
    const val VER: String = "@MOD_VERSION@"
    const val ID: String = "@MOD_ID@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        GlintConfig
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent?) {
        CommandManager.registerCommand(GlintCommand())
    }

}
