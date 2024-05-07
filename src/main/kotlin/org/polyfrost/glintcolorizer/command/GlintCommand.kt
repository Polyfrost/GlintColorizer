package org.polyfrost.glintcolorizer.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import org.polyfrost.glintcolorizer.GlintColorizer
import org.polyfrost.glintcolorizer.config.GlintConfig

@Command(
    value = GlintColorizer.ID,
    description = "Access the " + GlintColorizer.NAME + " GUI."
)
class GlintCommand {

    @Main
    fun handle() {
        GlintConfig.openGui()
    }

}