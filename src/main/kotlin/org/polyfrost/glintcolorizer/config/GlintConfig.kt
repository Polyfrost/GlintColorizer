package org.polyfrost.glintcolorizer.config


import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Accordion
import org.polyfrost.oneconfig.api.config.v1.annotations.Button
import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Color
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch


object GlintConfig : Config(
    "glintcolorizer.json", "/assets/glintcolorizer/glintcolorizer_dark.svg", "GlintColorizer", Category.VISUALS
) {
    val defaultColor = -8372020
    var oldGlintValue = -10407781

    @Switch(title = "Enabled")
    var enabled = true

    @Button(
        title = "Reset ALL Shiny Pots Settings",
        category = "Global",
        text = "Reset",
        description = "Resets ALL custom shiny pots settings."
    )
    fun resetShinyPots() {
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
    var globalColor = PolyColor(defaultColor)

    @Button(
        title = "Apply Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies your global glint color. Resets ALL custom colors."
    )
    fun applyColors() {
        /* Singular Colors */
        
        heldItem.glintColor.argb = globalColor.argb
        heldItem.glintColor.chromaSpeed = globalColor.chromaSpeed
        guiItem.glintColor.argb = globalColor.argb
        guiItem.glintColor.chromaSpeed = globalColor.chromaSpeed
        droppedItem.glintColor.argb = globalColor.argb
        droppedItem.glintColor.chromaSpeed = globalColor.chromaSpeed
        framedItem.glintColor.argb = globalColor.argb
        framedItem.glintColor.chromaSpeed = globalColor.chromaSpeed
        shinyPots.glintColor.argb = globalColor.argb
        shinyPots.glintColor.chromaSpeed = globalColor.chromaSpeed
        armorColor.argb = globalColor.argb
        armorColor.chromaSpeed = globalColor.chromaSpeed

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
    fun oldGlint() {
        heldItem.glintColor.argb = oldGlintValue
        /* GUI Items' glint color are actually the default 1.8 glint color! */
        droppedItem.glintColor.argb = oldGlintValue
        framedItem.glintColor.argb = oldGlintValue
        shinyPots.glintColor.argb = oldGlintValue
    }

    /* Held Items */
    @Accordion(
        category = "Held Item"
    )
    var heldItem = GlintEffectOptions()

    /* Gui Items */
    @Accordion(
        category = "GUI Item"
    )
    var guiItem = GlintEffectOptions()

    /* Dropped Items */
    @Accordion(
        category = "Dropped Item"
    )
    var droppedItem = GlintEffectOptions()

    /* Framed Items */
    @Accordion(
        category = "Framed Item"
    )
    var framedItem = GlintEffectOptions()

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
    var armorColor = PolyColor(defaultColor)

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

    @Accordion(
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