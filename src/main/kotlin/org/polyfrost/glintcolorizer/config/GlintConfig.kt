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
    var resetColors: Runnable = (Runnable {
        /* Singular Colors */
        heldColor = OneColor(defaultColor)
        guiColor = OneColor(defaultColor)
        droppedColor = OneColor(defaultColor)
        framedColor = OneColor(defaultColor)
        shinyColor = OneColor(defaultColor)
        armorColor = OneColor(defaultColor)
        /* Individual Strokes Colors */
        heldStrokeOne = OneColor(defaultColor)
        heldStrokeTwo = OneColor(defaultColor)
        guiStrokeOne = OneColor(defaultColor)
        guiStrokeTwo = OneColor(defaultColor)
        droppedStrokeOne = OneColor(defaultColor)
        droppedStrokeTwo = OneColor(defaultColor)
        shinyStrokeOne = OneColor(defaultColor)
        shinyStrokeTwo = OneColor(defaultColor)
        framedStrokeOne = OneColor(defaultColor)
        framedStrokeTwo = OneColor(defaultColor)
        /* Stroke */
        heldIndividualStrokes = false
        guiIndividualStrokes = false
        droppedIndividualStrokes = false
        framedIndividualStrokes = false
    })

    @Button(
        name = "Reset ALL Shiny Pots Settings",
        category = "Global",
        text = "Reset",
        description = "Resets ALL custom shiny pots settings."
    )
    var resetShinyPots: Runnable = (Runnable {
        potionGlint = false
        potionGlintSize = false
        potionGlintBackground = false
        potionBasedColor = false
        potionGlintForeground = false
    })

    @Color(
        name = "Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        description = "Modifies the color of the enchantment glint."
    )
    var globalColor: OneColor = OneColor(defaultColor)

    @Button(
        name = "Apply Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies your global glint color. Resets ALL custom colors."
    )
    var applyColors: Runnable = (Runnable {
        /* Singular Colors */
        heldColor = OneColor(globalColor.rgb)
        guiColor = OneColor(globalColor.rgb)
        droppedColor = OneColor(globalColor.rgb)
        framedColor = OneColor(globalColor.rgb)
        shinyColor = OneColor(globalColor.rgb)
        armorColor = OneColor(globalColor.rgb)
        /* Stroke */
        heldIndividualStrokes = false
        guiIndividualStrokes = false
        droppedIndividualStrokes = false
        framedIndividualStrokes = false
    })

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
    @Button(
        name = "Reset Held Glint Colors",
        category = "Held Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetHeld: Runnable = (Runnable {
        heldColor = OneColor(defaultColor)
        heldStrokeOne = OneColor(defaultColor)
        heldStrokeTwo = OneColor(defaultColor)
    })

    @Button(
        name = "Reset Held Glint Transformations",
        category = "Held Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint transformations."
    )
    var resetHeld2: Runnable = (Runnable {
        heldSpeed = 1.0F
        heldStrokeRotOne = -50.0F
        heldStrokeRotTwo = 10.0F
    })

    @Switch(
        name = "Modify Strokes Individually",
        category = "Held Item",
        subcategory = "Color"
    )
    var heldIndividualStrokes: Boolean = false

    @Color(
        name = "Held Item Glint Color",
        category = "Held Item",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var heldColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Held Item",
        subcategory = "Color",
        description = "Modifies the first stroke of the enchantment glint effect."
    )
    var heldStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Held Item",
        subcategory = "Color",
        description = "Modifies the second stroke of the enchantment glint effect."
    )
    var heldStrokeTwo: OneColor = OneColor(defaultColor)

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
    @Button(
        name = "Reset GUI Item Glint Colors",
        category = "GUI Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetGui: Runnable = (Runnable {
        guiColor = OneColor(defaultColor)
        guiStrokeOne = OneColor(defaultColor)
        guiStrokeTwo = OneColor(defaultColor)
    })

    @Switch(
        name = "Modify Strokes Individually",
        category = "GUI Item ",
        subcategory = "Color"
    )
    var guiIndividualStrokes: Boolean = false

    @Color(
        name = "GUI Item Glint Color",
        category = "GUI Item",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var guiColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "GUI Item",
        subcategory = "Color",
        description = "Modifies the first stroke of the enchantment glint effect."
    )
    var guiStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "GUI Item",
        subcategory = "Color",
        description = "Modifies the second stroke of the enchantment glint effect."
    )
    var guiStrokeTwo: OneColor = OneColor(defaultColor)

    /* Dropped Items */
    @Button(
        name = "Reset Dropped Item Glint Colors",
        category = "Dropped Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetDropped: Runnable = (Runnable {
        droppedColor = OneColor(defaultColor)
        droppedStrokeOne = OneColor(defaultColor)
        droppedStrokeTwo = OneColor(defaultColor)
    })

    @Switch(
        name = "Modify Strokes Individually",
        category = "Dropped Item",
        subcategory = "Color"
    )
    var droppedIndividualStrokes: Boolean = false

    @Color(
        name = "Dropped Item Glint Color",
        category = "Dropped Item",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var droppedColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Dropped Item",
        subcategory = "Color",
        description = "Modifies the first stroke of the enchantment glint effect."
    )
    var droppedStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Dropped Item",
        subcategory = "Color",
        description = "Modifies the second stroke of the enchantment glint effect."
    )
    var droppedStrokeTwo: OneColor = OneColor(defaultColor)

    /* Framed Items */
    @Button(
        name = "Reset Framed Item Glint Colors",
        category = "Framed Item",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetFramed: Runnable = (Runnable {
        droppedColor = OneColor(defaultColor)
        droppedStrokeOne = OneColor(defaultColor)
        droppedStrokeTwo = OneColor(defaultColor)
    })

    @Switch(
        name = "Modify Strokes Individually",
        category = "Framed Item",
        subcategory = "Color"
    )
    var framedIndividualStrokes: Boolean = false

    @Color(
        name = "Framed Item Glint Color",
        category = "Framed Item",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var framedColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Framed Item",
        subcategory = "Color",
        description = "Modifies the first stroke of the enchantment glint effect."
    )
    var framedStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Framed Item",
        subcategory = "Color",
        description = "Modifies the second stroke of the enchantment glint effect."
    )
    var framedStrokeTwo: OneColor = OneColor(defaultColor)

    /* Armor */
    @Button(
        name = "Reset Armor Glint",
        category = "Armor",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetArmor: Runnable = (Runnable {
        armorColor = OneColor(defaultColor)
    })

    @Color(
        name = "Armor Glint Color",
        category = "Armor",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var armorColor: OneColor = OneColor(defaultColor)

    /* Shiny Pots */
    @Button(
        name = "Reset Shiny Pots Glint Colors",
        category = "Shiny Pots",
        subcategory = "Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetShiny: Runnable = (Runnable {
        shinyColor = OneColor(defaultColor)
        shinyStrokeOne = OneColor(defaultColor)
        shinyStrokeTwo = OneColor(defaultColor)
        potionBasedColor = false
        potionGlintBackground = false
        potionGlintForeground = false
    })

    @Switch(
        name = "Shiny Potions",
        category = "Shiny Pots"
    )
    var potionGlint: Boolean = false

    @Checkbox(
        name = "Render Over Full Slot",
        category = "Shiny Pots"
    )
    var potionGlintSize: Boolean = false

    @Checkbox(
        name = "Render Shiny Effect Only",
        description = "Disables the enchantment glint on the potion and solely renders the shiny effect.",
        category = "Shiny Pots"
    )
    var potionGlintForeground: Boolean = false

    @Checkbox(
        name = "Custom Shiny Effect Color",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionGlintBackground: Boolean = false

    @Switch(
        name = "Potion Color Based Glint",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var potionBasedColor: Boolean = false

    @Switch(
        name = "Modify the Shiny Effect's Strokes Individually",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var shinyIndividualStrokes: Boolean = false

    @Color(
        name = "Shiny Glint Effect Color",
        category = "Shiny Pots",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var shinyColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Shiny Pots",
        subcategory = "Color",
        description = "Modifies the first stroke of the shiny glint effect."
    )
    var shinyStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Shiny Pots",
        subcategory = "Color",
        description = "Modifies the second stroke of the shiny glint effect."
    )
    var shinyStrokeTwo: OneColor = OneColor(defaultColor)

    init {
        initialize()

//        addDependency("potionGlintType", "potionGlint")
//        addDependency("strokeOne", "individualStrokes")
//        addDependency("strokeTwo", "individualStrokes")
    }

}