package org.polyfrost.glintcolorizer.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator
import net.minecraft.client.Minecraft
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
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors settings and defaults them back to the vanilla color."
    )
    var resetColors: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        /* Singular Colors */
        heldColor = OneColor(defaultColor)
        guiColor = OneColor(defaultColor)
        droppedColor = OneColor(defaultColor)
        framedColor = OneColor(defaultColor)
        shinyColor = OneColor(defaultColor)
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

        GlintConfig.save()
        openGui()
    })

    @Button(
        name = "Reset ALL Shiny Pots Settings",
        category = "Global",
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom shiny pots settings"
    )
    var resetShinyPots: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        potionGlint = false
        potionGlintSize = false
        potionGlintBackground = false
        potionBasedColor = false
        potionGlintForeground = false
        GlintConfig.save()
        openGui()
    })

//    @Button(
//        name = "Sync Armor Glint With Item Glint",
//        category = "Global",
//        subcategory = "Color Configuration",
//        text = "Sync",
//        description = "Syncs the armor glint color with the item glint color."
//    )
//    var syncAtoI: Runnable = (Runnable {
//        Minecraft.getMinecraft().displayGuiScreen(null)
//        guiStrokeOne = OneColor(defaultColor)
//        guiStrokeTwo = OneColor(defaultColor)
//        save()
//        openGui()
//    })
//
//    @Button(
//        name = "Sync Item Glint With Armor Glint",
//        category = "Global",
//        subcategory = "Color Configuration",
//        text = "Sync",
//        description = "Syncs the item glint color with the armor glint color ."
//    )
//    var syncItoA: Runnable = (Runnable {
//        Minecraft.getMinecraft().displayGuiScreen(null)
//        guiStrokeOne = OneColor(defaultColor)
//        guiStrokeTwo = OneColor(defaultColor)
//        save()
//        openGui()
//    })

    /* Held Items */
    @Button(
        name = "Reset Held Glint Colors",
        category = "Held Item Glint Color",
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetHeld: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        heldColor = OneColor(defaultColor)
        heldStrokeOne = OneColor(defaultColor)
        heldStrokeTwo = OneColor(defaultColor)
        GlintConfig.save()
        openGui()
    })

    @Switch(
        name = "Modify glint strokes individually",
        category = "Held Item Glint Color",
    )
    var heldIndividualStrokes: Boolean = false

    @Color(
        name = "Held Item Glint Color",
        category = "Held Item Glint Color",
        description = "Modifies the color of the enchantment glint."
    )
    var heldColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Held Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var heldStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Held Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var heldStrokeTwo: OneColor = OneColor(defaultColor)

    /* Gui Items */
    @Button(
        name = "Reset GUI Item Glint Colors",
        category = "GUI Item Glint Color",
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetGui: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        guiColor = OneColor(defaultColor)
        guiStrokeOne = OneColor(defaultColor)
        guiStrokeTwo = OneColor(defaultColor)
        GlintConfig.save()
        openGui()
    })

    @Switch(
        name = "Modify glint strokes individually",
        category = "GUI Item Glint Color",
    )
    var guiIndividualStrokes: Boolean = false

    @Color(
        name = "GUI Item Glint Color",
        category = "GUI Item Glint Color",
        description = "Modifies the color of the enchantment glint."
    )
    var guiColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "GUI Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var guiStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "GUI Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var guiStrokeTwo: OneColor = OneColor(defaultColor)

    /* Dropped Items */
    @Button(
        name = "Reset Dropped Item Glint Colors",
        category = "Dropped Item Glint Color",
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetDropped: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        droppedColor = OneColor(defaultColor)
        droppedStrokeOne = OneColor(defaultColor)
        droppedStrokeTwo = OneColor(defaultColor)
        GlintConfig.save()
        openGui()
    })

    @Switch(
        name = "Modify glint strokes individually",
        category = "Dropped Item Glint Color",
    )
    var droppedIndividualStrokes: Boolean = false

    @Color(
        name = "Dropped Item Glint Color",
        category = "Dropped Item Glint Color",
        description = "Modifies the color of the enchantment glint."
    )
    var droppedColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Dropped Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var droppedStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Dropped Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var droppedStrokeTwo: OneColor = OneColor(defaultColor)

    /* Framed Items */
    @Button(
        name = "Reset Framed Item Glint Colors",
        category = "Framed Item Glint Color",
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetFramed: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        droppedColor = OneColor(defaultColor)
        droppedStrokeOne = OneColor(defaultColor)
        droppedStrokeTwo = OneColor(defaultColor)
        GlintConfig.save()
        openGui()
    })

    @Switch(
        name = "Modify glint strokes individually",
        category = "Framed Item Glint Color",
    )
    var framedIndividualStrokes: Boolean = false

    @Color(
        name = "Framed Item Glint Color",
        category = "Framed Item Glint Color",
        description = "Modifies the color of the enchantment glint."
    )
    var framedColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Framed Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var framedStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Framed Item Glint Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var framedStrokeTwo: OneColor = OneColor(defaultColor)

    /* Armor */
    @Color(
        name = "Armor Glint Color",
        category = "Armor Glint Color",
        description = "Modifies the color of the enchantment glint."
    )
    var armorColor: OneColor = OneColor(defaultColor)

    /* Shiny Pots */
    @Button(
        name = "Reset Shiny Pots Glint Colors",
        category = "Shiny Pots",
        subcategory = "Color Configuration",
        text = "Reset",
        description = "Resets ALL custom glint colors."
    )
    var resetShiny: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        shinyColor = OneColor(defaultColor)
        shinyStrokeOne = OneColor(defaultColor)
        shinyStrokeTwo = OneColor(defaultColor)
        potionBasedColor = false
        potionGlintBackground = false
        potionGlintForeground = false
        GlintConfig.save()
        openGui()
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
        name = "Modify glint strokes individually",
        category = "Shiny Pots",
        subcategory = "Color"
    )
    var shinyIndividualStrokes: Boolean = false

    @Color(
        name = "Shiny Effect Glint Color",
        category = "Shiny Pots",
        subcategory = "Color",
        description = "Modifies the color of the enchantment glint."
    )
    var shinyColor: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 1 Color",
        category = "Shiny Pots",
        subcategory = "Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var shinyStrokeOne: OneColor = OneColor(defaultColor)

    @Color(
        name = "Stroke 2 Color",
        category = "Shiny Pots",
        subcategory = "Color",
        description = "Modifies one of the enchantment glint stroke colors."
    )
    var shinyStrokeTwo: OneColor = OneColor(defaultColor)

    init {
        initialize()

//        addDependency("potionGlintType", "potionGlint")
//        addDependency("strokeOne", "individualStrokes")
//        addDependency("strokeTwo", "individualStrokes")
    }

}