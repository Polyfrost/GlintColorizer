package org.polyfrost.glintcolorizer.command


import org.polyfrost.glintcolorizer.GlintColorizer
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Handler
import org.polyfrost.oneconfig.utils.v1.dsl.openUI

@Command(
    value = [GlintColorizer.ID],
    description = "Access the " + GlintColorizer.NAME + " GUI."
)
class GlintCommand {

    @Handler
    fun main() {
        GlintConfig.openUI()
    }

}