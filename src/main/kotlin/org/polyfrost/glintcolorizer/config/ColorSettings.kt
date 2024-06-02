package org.polyfrost.glintcolorizer.config

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.ConfigUtils
import cc.polyfrost.oneconfig.config.core.OneColor

class ColorSettings {

    @Button(
        name = "Reset Held Glint Colors",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetColor = Runnable {
        reset(false)
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

    fun reset(resetAll: Boolean) {
        val target = ColorSettings()
        val newFields = ConfigUtils.getClassFields(target::class.java)
        val fields = ConfigUtils.getClassFields(this::class.java)
        for (i in 0 until fields.size) {
            if (!resetAll && fields[i].type != OneColor::class.java) continue
            fields[i].set(this, ConfigUtils.getField(newFields[i], target))
        }
    }

}