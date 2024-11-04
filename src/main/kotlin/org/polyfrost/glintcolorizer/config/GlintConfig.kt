package org.polyfrost.glintcolorizer.config

import org.polyfrost.glintcolorizer.GlintColorizer
import org.polyfrost.glintcolorizer.config.annotation.ColorEntry
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Button
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Color
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polyui.color.argb

//
//object GlintConfig : Config(
//    Mod(
//        GlintColorizer.NAME, ModType.UTIL_QOL, "/glintcolorizer_dark.svg", VigilanceMigrator(
//            File(File(File("./W-OVERFLOW"), GlintColorizer.NAME), GlintColorizer.ID + ".toml").path
//        )
//    ), GlintColorizer.ID + ".json"
//) {

object GlintConfig : Config("${GlintColorizer.ID}.json", "/glintcolorizer_dark.svg", GlintColorizer.NAME, Category.QOL) {

    const val DEFAULT_COLOR = -8372020
    private var oldGlintValue = -10407781

    @Button(
        title = "Reset ALL Colors Settings",
        category = "Global",
        text = "Reset",
        description = "Resets ALL custom glint colors settings and defaults them back to the vanilla color."
    )
    var resetColors = Runnable {
        globalColor = argb(DEFAULT_COLOR)
        heldItem.reset(true)
        guiItem.reset(true)
        droppedItem.reset(true)
        framedItem.reset(true)
        shinyPots.reset(true)
        armorColor = argb(DEFAULT_COLOR)
    }

    @Button(
        title = "Reset ALL Transformation Settings",
        category = "Global",
        text = "Reset",
        description = "Resets ALL custom glint colors settings and defaults them back to the vanilla color."
    )
    var resetTransformations = Runnable {
        heldItem.reset2()
        guiItem.reset2()
        droppedItem.reset2()
        framedItem.reset2()
        shinyPots.reset2()
    }

    @Button(
        title = "Reset ALL Shiny Pots Settings",
        category = "Global",
        text = "Reset",
        description = "Resets ALL custom shiny pots settings."
    )
    var resetShinyPots = Runnable {
        potionGlint = false
        potionGlintSize = false
        potionGlintBackground = false
        potionBasedColor = false
        potionGlintForeground = false
    }

    @Color(
        title = "Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        description = "Modifies the color of the enchantment glint."
    )
    var globalColor = argb(DEFAULT_COLOR)

    @Button(
        title = "Apply Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies your global glint color. Resets ALL custom colors."
    )
    var applyColors = Runnable {
        /* Singular Colors */
        // TODO
//        heldItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
//        guiItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
//        droppedItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
//        framedItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
//        shinyPots.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
//        armorColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }

        /* Stroke */
        heldItem.individualStrokes = false
        guiItem.individualStrokes = false
        droppedItem.individualStrokes = false
        framedItem.individualStrokes = false
        shinyPots.individualStrokes = false
    }

    @Button(
        title = "1.7 Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies the 1.7 glint color to all transform types."
    )
    var oldGlint = Runnable {
        heldItem.glintColor = argb(oldGlintValue)
        /* GUI Items' glint color are actually the default 1.8 glint color! */
        droppedItem.glintColor = argb(oldGlintValue)
        framedItem.glintColor = argb(oldGlintValue)
        shinyPots.glintColor = argb(oldGlintValue)
    }

    /* Held Items */
    @ColorEntry(
        category = "Held Item"
    )
    var heldItem = GlintEffectOptions()

    /* Gui Items */
    @ColorEntry(
        category = "GUI Item"
    )
    var guiItem = GlintEffectOptions()

    /* Dropped Items */
    @ColorEntry(
        category = "Dropped Item"
    )
    var droppedItem = GlintEffectOptions()

    /* Framed Items */
    @ColorEntry(
        category = "Framed Item"
    )
    var framedItem = GlintEffectOptions()

    /* Armor */
    @Button(
        title = "Reset Armor Glint",
        category = "Armor",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetArmor = Runnable {
        armorColor = argb(DEFAULT_COLOR)
    }

    @Switch(
        title = "Disable Armor Glint",
        category = "Armor",
        subcategory = "Color",
        description = "Disables the enchantment glint on armor."
    )
    var armorGlintToggle = false

    @Color(
        title = "Armor Glint Color",
        category = "Armor",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var armorColor = argb(DEFAULT_COLOR)

    /* Shiny Pots */
    @Switch(
        title = "Shiny Potions",
        category = "Shiny Pots"
    )
    var potionGlint = false

    @Checkbox(
        title = "Render Over Full Slot",
        category = "Shiny Pots"
    )
    var potionGlintSize = false

    @Checkbox(
        title = "Render Shiny Effect Only",
        description = "Disables the enchantment glint on the potion and solely renders the shiny effect.",
        category = "Shiny Pots"
    )
    var potionGlintForeground = false

    @Checkbox(
        title = "Custom Shiny Effect Color",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionGlintBackground = false

    @ColorEntry(
        category = "Shiny Pots"
    )
    var shinyPots = GlintEffectOptions()

    @Switch(
        title = "Potion Color Based Glint",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionBasedColor = false

}