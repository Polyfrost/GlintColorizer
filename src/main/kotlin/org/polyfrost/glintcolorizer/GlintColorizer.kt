package org.polyfrost.glintcolorizer

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import org.polyfrost.glintcolorizer.command.GlintCommand
import org.polyfrost.glintcolorizer.config.GlintConfig

@Mod(
    modid = GlintColorizer.ID,
    name = GlintColorizer.NAME,
    version = GlintColorizer.VER,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object GlintColorizer {

    const val NAME: String = "@NAME@"
    const val VER: String = "@VER@"
    const val ID: String = "@ID@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        GlintConfig
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent?) {
        CommandManager.INSTANCE.registerCommand(GlintCommand())
    }

}
