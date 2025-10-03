package org.polyfrost.glintcolorizer.config

import org.polyfrost.oneconfig.api.config.v1.annotations.Color
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polyui.color.argb

open class BaseGlint {
    @Switch(title = "Enable Glint Visual")
    var enabled = true

    @Switch(title = "Modify Strokes Individually")
    var individualStrokes = true

    @Color(title = "Glint Color", description = "Modifies the color of the enchantment glint.")
    var glintColor = argb(GlintConfig.DEFAULT_GLINT_COLOR)

    @Color(title = "Stroke 1 Color", description = "Modifies the first stroke of the enchantment glint effect.")
    var strokeOneColor = argb(0xFFFF0000.toInt()) // argb(GlintConfig.DEFAULT_GLINT_COLOR)

    @Slider(title = "Stroke 1 Rotation", min = -180.0F, max = 180.0F)
    var firstStrokeRotation = -50.0F

    @Color(title = "Stroke 2 Color", description = "Modifies the second stroke of the enchantment glint effect.")
    var strokeTwoColor = argb(0xFF0AEAFF.toInt()) // argb(GlintConfig.DEFAULT_GLINT_COLOR)

    @Slider(title = "Stroke 2 Rotation", min = -180.0F, max = 180.0F)
    var secondStrokeRotation = 10.0F

    @Slider(title = "Speed", min = 0.1F, max = 10.0F)
    var speed = 1.0F

    @Slider(title = "Scale", min = 0.0F, max = 8.0F)
    var scale = 1.0F
}