package org.polyfrost.glintcolorizer

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.utils.v1.dsl.openUI

@Mod(
    modid = GlintColorizer.ID,
    name = GlintColorizer.NAME,
    version = GlintColorizer.VER,
    modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter"
)
object GlintColorizer {
    const val NAME: String = "@MOD_NAME@"
    const val VER: String = "@MOD_VERSION@"
    const val ID: String = "@MOD_ID@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        GlintConfig
        CommandManager.register(CommandManager.literal("glintcolorizer").executes {
            GlintConfig.openUI()
            1
        })
    }
}
