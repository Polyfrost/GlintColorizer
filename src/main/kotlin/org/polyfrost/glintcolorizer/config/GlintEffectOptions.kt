package org.polyfrost.glintcolorizer.config

import org.polyfrost.oneconfig.api.config.v1.annotations.Button
import org.polyfrost.oneconfig.api.config.v1.annotations.Color
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polyui.color.argb
import org.polyfrost.utils.v1.dsl.openUI

class GlintEffectOptions {

    @Button(
        title = "Reset Glint Colors",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetColor = Runnable {
        reset(false)
        GlintConfig.save()
        GlintConfig.openUI()
    }

    @Button(
        title = "Reset Transformations",
        category = "Held Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint transformations."
    )
    var resetTransforms = Runnable {
        reset2()
        GlintConfig.save()
        GlintConfig.openUI()
    }

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
    var glintColor = argb(GlintConfig.DEFAULT_COLOR)

    @Color(
        title = "Stroke 1 Color",
        subcategory = "Color",
        description = "Modifies the first stroke of the enchantment glint effect."
    )
    var strokeOneColor = argb(GlintConfig.DEFAULT_COLOR)

    @Color(
        title = "Stroke 2 Color",
        subcategory = "Color",
        description = "Modifies the second stroke of the enchantment glint effect."
    )
    var strokeTwoColor = argb(GlintConfig.DEFAULT_COLOR)

    @Slider(
        title = "Speed",
        subcategory = "Speed",
        min = 0.1F,
        max = 10.0F,
//        instant = true
    )
    var speed = 1.0F

    @Slider(
        title = "Stroke 1 Rotation",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F,
//        instant = true
    )
    var strokeRotOne = -50.0F

    @Slider(
        title = "Stroke 2 Rotation",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F,
//        instant = true
    )
    var strokeRotTwo = 10.0F

    @Slider(
        title = "Scale",
        subcategory = "Scale",
        min = 0.0F,
        max = 8.0F,
//        instant = true
    )
    var scale = 1.0F

    fun reset(resetAll: Boolean) {
        // TODO
//        val target = GlintEffectOptions()
//        val newFields = ConfigUtils.getClassFields(target::class.java)
//        val fields = ConfigUtils.getClassFields(this::class.java)
//        for (i in 0 until fields.size) {
//            if (!resetAll && fields[i].type != OneColor::class.java) continue
//            fields[i].set(this, ConfigUtils.getField(newFields[i], target))
//        }
    }

    fun reset2() {
        speed = 1.0F
        strokeRotOne = -50.0F
        strokeRotTwo = 10.0F
        scale = 1.0F
    }

}