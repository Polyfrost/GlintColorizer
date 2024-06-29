package org.polyfrost.glintcolorizer.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator
import org.polyfrost.glintcolorizer.GlintColorizer
import org.polyfrost.glintcolorizer.config.annotation.ColorEntry
import cc.polyfrost.oneconfig.config.annotations.Color
import java.io.File

object GlintConfig : Config(
    Mod(
        GlintColorizer.NAME, ModType.UTIL_QOL, "/glintcolorizer_dark.svg", VigilanceMigrator(
            File(File(File("./W-OVERFLOW"), GlintColorizer.NAME), GlintColorizer.ID + ".toml").path
        )
    ), GlintColorizer.ID + ".json"
) {

    @Exclude val defaultColor = -8372020
    @Exclude var oldGlintValue = -10407781

    @Button(
        name = "Reset ALL Colors Settings",
        category = "Global",
        text = "Reset",
        description = "Resets ALL custom glint colors settings and defaults them back to the vanilla color."
    )
    var resetColors = Runnable {
        globalColor = OneColor(defaultColor)
        heldItem.reset(true)
        guiItem.reset(true)
        droppedItem.reset(true)
        framedItem.reset(true)
        shinyPots.reset(true)
        armorColor = OneColor(defaultColor)
    }

    @Button(
        name = "Reset ALL Transformation Settings",
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
        name = "Reset ALL Shiny Pots Settings",
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
        name = "Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        description = "Modifies the color of the enchantment glint."
    )
    var globalColor = OneColor(defaultColor)

    @Button(
        name = "Apply Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies your global glint color. Resets ALL custom colors."
    )
    var applyColors = Runnable {
        /* Singular Colors */
        heldItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
        guiItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
        droppedItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
        framedItem.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
        shinyPots.glintColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
        armorColor = globalColor.clone().also { it.setChromaSpeed(globalColor.dataBit) }
        /* Stroke */
        heldItem.individualStrokes = false
        guiItem.individualStrokes = false
        droppedItem.individualStrokes = false
        framedItem.individualStrokes = false
        shinyPots.individualStrokes = false
    }

    @Button(
        name = "1.7 Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies the 1.7 glint color to all transform types."
    )
    var oldGlint = Runnable {
        heldItem.glintColor = OneColor(oldGlintValue)
        /* GUI Items' glint color are actually the default 1.8 glint color! */
        droppedItem.glintColor = OneColor(oldGlintValue)
        framedItem.glintColor = OneColor(oldGlintValue)
        shinyPots.glintColor = OneColor(oldGlintValue)
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
        name = "Reset Armor Glint",
        category = "Armor",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetArmor = Runnable {
        armorColor = OneColor(defaultColor)
    }

    @Switch(
        name = "Disable Armor Glint",
        category = "Armor",
        subcategory = "Color",
        description = "Disables the enchantment glint on armor."
    )
    var armorGlintToggle = false

    @Color(
        name = "Armor Glint Color",
        category = "Armor",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var armorColor = OneColor(defaultColor)

    /* Shiny Pots */
    @Switch(
        name = "Shiny Potions",
        category = "Shiny Pots"
    )
    var potionGlint = false

    @Checkbox(
        name = "Render Over Full Slot",
        category = "Shiny Pots"
    )
    var potionGlintSize = false

    @Checkbox(
        name = "Render Shiny Effect Only",
        description = "Disables the enchantment glint on the potion and solely renders the shiny effect.",
        category = "Shiny Pots"
    )
    var potionGlintForeground = false

    @Checkbox(
        name = "Custom Shiny Effect Color",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionGlintBackground = false

    @ColorEntry(
        category = "Shiny Pots"
    )
    var shinyPots = GlintEffectOptions()

    @Switch(
        name = "Potion Color Based Glint",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionBasedColor = false

    init {
        initialize()
    }

}