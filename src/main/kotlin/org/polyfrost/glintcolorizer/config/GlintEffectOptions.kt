package org.polyfrost.glintcolorizer.config

import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.annotations.Color
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch


class GlintEffectOptions {
    @Switch(
        title = "Modify Strokes Individually",
        subcategory = "Color"
    )
    var individualStrokes = false

    @Color(
        title = "Glint Color",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var glintColor = PolyColor(GlintConfig.DEFAULT_COLOR)

    @Color(
        title = "Stroke 1 Color",
        subcategory = "Color",
        description = "Modifies the first stroke of the enchantment glint effect."
    )
    var strokeOneColor = PolyColor(GlintConfig.DEFAULT_COLOR)

    @Color(
        title = "Stroke 2 Color",
        subcategory = "Color",
        description = "Modifies the second stroke of the enchantment glint effect."
    )
    var strokeTwoColor = PolyColor(GlintConfig.DEFAULT_COLOR)

    @Slider(
        title = "Speed",
        subcategory = "Speed",
        min = 0.1F,
        max = 10.0F
    )
    var speed = 1.0F

    @Slider(
        title = "Stroke 1 Rotation",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F
    )
    var strokeRotOne = -50.0F

    @Slider(
        title = "Stroke 2 Rotation",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F
    )
    var strokeRotTwo = 10.0F

    @Slider(
        title = "Scale",
        subcategory = "Scale",
        min = 0.0F,
        max = 8.0F
    )
    var scale = 1.0F

    fun reset() {
        individualStrokes = false
        resetColor(glintColor)
        resetColor(strokeOneColor)
        resetColor(strokeTwoColor)
        speed = 1.0F
        strokeRotOne = -50.0F
        strokeRotTwo = 10.0F
        scale = 1.0F
    }

    private fun resetColor(color: PolyColor) {
        color.argb = GlintConfig.DEFAULT_COLOR
        color.chroma = false
        color.chromaSpeed = 1.0F
    }
}