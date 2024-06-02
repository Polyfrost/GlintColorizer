package org.polyfrost.glintcolorizer.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator
import org.polyfrost.glintcolorizer.GlintColorizer
import java.io.File

object GlintConfig : Config(
    Mod(
        GlintColorizer.NAME, ModType.UTIL_QOL, "/glintcolorizer_dark.svg", VigilanceMigrator(
            File(File(File("./W-OVERFLOW"), GlintColorizer.NAME), GlintColorizer.ID + ".toml").path
        )
    ), GlintColorizer.ID + ".json"
) {

    @Exclude val defaultColor = -8372020

    @Button(
        name = "Reset ALL Colors Settings",
        category = "Global",
        text = "Reset",
        description = "Resets ALL custom glint colors settings and defaults them back to the vanilla color."
    )
    var resetColors = Runnable {
        heldItem.reset(true)
        guiItem.reset(true)
        droppedItem.reset(true)
        framedItem.reset(true)
        shinyPots.reset(true)
        armorColor = OneColor(defaultColor)
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
        heldItem.glintColor = OneColor(globalColor.rgb)
        guiItem.glintColor = OneColor(globalColor.rgb)
        droppedItem.glintColor = OneColor(globalColor.rgb)
        framedItem.glintColor = OneColor(globalColor.rgb)
        shinyPots.glintColor = OneColor(globalColor.rgb)
        armorColor = OneColor(globalColor.rgb)
        /* Stroke */
        heldItem.individualStrokes = false
        guiItem.individualStrokes = false
        droppedItem.individualStrokes = false
        framedItem.individualStrokes = false
        shinyPots.individualStrokes = false
    }

//    @Button(
//        name = "Sync Armor Glint With Item Glint",
//        category = "Global",
//        subcategory = "Configuration",
//        text = "Sync",
//        description = "Syncs the armor glint color with the item glint color."
//    )
//    var syncAtoI: Runnable = (Runnable {
//        guiStrokeOne = OneColor(defaultColor)
//        guiStrokeTwo = OneColor(defaultColor)
//    })
//
//    @Button(
//        name = "Sync Item Glint With Armor Glint",
//        category = "Global",
//        subcategory = "Configuration",
//        text = "Sync",
//        description = "Syncs the item glint color with the armor glint color ."
//    )
//    var syncItoA: Runnable = (Runnable {
//        guiStrokeOne = OneColor(defaultColor)
//        guiStrokeTwo = OneColor(defaultColor)
//    })

    /* Held Items */
    @ColorEntry(
        category = "Held Item"
    )
    var heldItem = ColorSettings()

    @Button(
        name = "Reset Transformations",
        category = "Held Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint transformations."
    )
    var resetHeld2 = Runnable {
        heldSpeed = 1.0F
        heldStrokeRotOne = -50.0F
        heldStrokeRotTwo = 10.0F
    }

    @Slider(
        name = "Speed",
        category = "Held Item",
        subcategory = "Speed",
        min = 0.1F,
        max = 10.0F,
        instant = true
    )
    var heldSpeed = 1.0F

    @Slider(
        name = "Stroke 1 Rotation",
        category = "Held Item",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F,
        instant = true
    )
    var heldStrokeRotOne = -50.0F

    @Slider(
        name = "Stroke 2 Rotation",
        category = "Held Item",
        subcategory = "Rotation",
        min = -180.0F,
        max = 180.0F,
        instant = true
    )
    var heldStrokeRotTwo = 10.0F

    /* Gui Items */
    @ColorEntry(
        category = "GUI Item"
    )
    var guiItem = ColorSettings()

    /* Dropped Items */
    @ColorEntry(
        category = "Dropped Item"
    )
    var droppedItem = ColorSettings()

    /* Framed Items */
    @ColorEntry(
        category = "Framed Item"
    )
    var framedItem = ColorSettings()

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

    @ColorEntry(
        category = "Shiny Pots"
    )
    var shinyPots = ColorSettings()

    @Checkbox(
        name = "Custom Shiny Effect Color",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionGlintBackground = false

    @Switch(
        name = "Potion Color Based Glint",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionBasedColor = false

    init {
        initialize()

//        addDependency("potionGlintType", "potionGlint")
//        addDependency("strokeOne", "individualStrokes")
//        addDependency("strokeTwo", "individualStrokes")
    }

}