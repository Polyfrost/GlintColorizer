package org.polyfrost.glintcolorizer.config

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.ConfigUtils
import cc.polyfrost.oneconfig.config.core.OneColor

class GlintEffectOptions {

    @Button(
        name = "Reset Held Glint Colors",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetColor = Runnable {
        reset(false)
    }

    @Button(
        name = "Reset Transformations",
        category = "Held Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint transformations."
    )
    var resetTransforms = Runnable {
        reset2()
    }

    @Switch(
        name = "Modify Strokes Individually",
        subcategory = "Color"
    )
    var individualStrokes = false

    @Color(
        name = "Glint Color",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var glintColor = OneColor(GlintConfig.defaultColor)

    @Color(
        name = "Stroke 1 Color",
        subcategory = "Color",
        description = "Modifies the first stroke of the enchantment glint effect."
    )
    var strokeOneColor = OneColor(GlintConfig.defaultColor)

    @Color(
        name = "Stroke 2 Color",
        subcategory = "Color",
        description = "Modifies the second stroke of the enchantment glint effect."
    )
    var strokeTwoColor = OneColor(GlintConfig.defaultColor)

    @Slider(
        name = "Speed",
        subcategory = "Speed",
        min = 0.1F,
        max = 10.0F,
        instant = true
    )
    var speed = 1.0F

    @Slider(
        name = "Stroke 1 Rotation",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F,
        instant = true
    )
    var strokeRotOne = -50.0F

    @Slider(
        name = "Stroke 2 Rotation",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F,
        instant = true
    )
    var strokeRotTwo = 10.0F

    @Slider(
        name = "Scale",
        subcategory = "Scale",
        min = 0.0F,
        max = 8.0F,
        instant = true
    )
    var scale = 1.0F

    fun reset(resetAll: Boolean) {
        val target = GlintEffectOptions()
        val newFields = ConfigUtils.getClassFields(target::class.java)
        val fields = ConfigUtils.getClassFields(this::class.java)
        for (i in 0 until fields.size) {
            if (!resetAll && fields[i].type != OneColor::class.java) continue
            fields[i].set(this, ConfigUtils.getField(newFields[i], target))
        }
    }

    fun reset2() {
        speed = 1.0F
        strokeRotOne = -50.0F
        strokeRotTwo = 10.0F
        scale = 1.0F
    }

}