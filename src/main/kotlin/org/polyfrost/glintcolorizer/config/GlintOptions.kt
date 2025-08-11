package org.polyfrost.glintcolorizer.config

import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Color
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polyui.color.argb

open class GlintOptions {
    @Switch(title = "Modify Strokes Individually")
    var individualStrokes = true

    @Color(title = "Glint Color", description = "Modifies the color of the enchantment glint.")
    var glintColor = argb(GlintConfig.DEFAULT_GLINT_COLOR)

    @Color(title = "Stroke 1 Color", description = "Modifies the first stroke of the enchantment glint effect.")
    var strokeOneColor = argb(0xFFFF0000.toInt()) // argb(GlintConfig.DEFAULT_GLINT_COLOR)

    @Color(title = "Stroke 2 Color", description = "Modifies the second stroke of the enchantment glint effect.")
    var strokeTwoColor = argb(0xFF0AEAFF.toInt()) // argb(GlintConfig.DEFAULT_GLINT_COLOR)

    @Slider(title = "Speed", min = 0.1F, max = 10.0F)
    var speed = 1.0F

    @Slider(title = "Stroke 1 Rotation", min = -180.0F, max = 180.0F)
    var firstStrokeRotation = -50.0F

    @Slider(title = "Stroke 2 Rotation", min = -180.0F, max = 180.0F)
    var secondStrokeRotation = 10.0F

    @Slider(title = "Scale", min = 0.0F, max = 8.0F)
    var scale = 1.0F

    class ShinyPots : GlintOptions() {
        @Switch(title = "Shiny Potions")
        var usePotionGlint = false

        @Checkbox(title = "Render Over Full Slot")
        var useFullSlotShine = false

        @Checkbox(
            title = "Render Shiny Effect Only",
            description = "Disables the enchantment glint on the potion and solely renders the shiny effect.",
        )
        var disablePotionGlint = false

        @Checkbox(title = "Custom Shiny Effect Color")
        var useCustomColor = false

        @Switch(title = "Potion Color Based Glint")
        var usePotionBasedColor = false
    }
}