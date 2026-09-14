package org.polyfrost.glintcolorizer


import net.fabricmc.api.ClientModInitializer
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents

import org.polyfrost.glintcolorizer.command.GlintCommand
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.oneconfig.api.commands.v1.CommandManager

object GlintColorizer : ClientModInitializer {
    const val NAME: String = "@MOD_NAME@"
    const val VERSION: String = "@MOD_VERSION@"
    const val ID: String = "@MOD_ID@"

    override fun onInitializeClient() {
        CommandManager.register(GlintCommand())
        MinecraftClientEvents.READY.register() { context: Any -> GlintConfig.preload() }
    }
}
