package org.polyfrost.glintcolorizer.config

import org.polyfrost.glintcolorizer.GlintColorizer
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Accordion
import org.polyfrost.oneconfig.api.config.v1.annotations.Button
import org.polyfrost.oneconfig.api.config.v1.annotations.Color
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polyui.color.argb

object GlintConfig : Config("${GlintColorizer.ID}.toml", "/glintcolorizer_dark.svg", GlintColorizer.NAME, Category.QOL) {
    const val DEFAULT_GLINT_COLOR = -8372020
    const val OLD_GLINT_COLOR = -10407781

    @Color(
        title = "Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        description = "Modifies the color of the enchantment glint."
    )
    var globalColor = argb(DEFAULT_GLINT_COLOR)

    @Button(
        title = "1.7 Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies the 1.7 glint color to all transform types."
    )
    private val oldGlint = Runnable {
        heldItemOptions.glintColor = argb(OLD_GLINT_COLOR)
        /* GUI Items' glint color are actually the default 1.8 glint color! */
        droppedItemOptions.glintColor = argb(OLD_GLINT_COLOR)
        framedItemOptions.glintColor = argb(OLD_GLINT_COLOR)
        shinyPotsOptions.glintColor = argb(OLD_GLINT_COLOR)
    }

    @Switch(
        title = "Disable Armor Glint",
        subcategory = "Armor",
        description = "Disables the enchantment glint on armor."
    )
    var isArmorGlintDisabled = false

    @Color(
        title = "Armor Glint Color",
        subcategory = "Armor",
        description = "Modifies the color of the enchantment glint."
    )
    var armorColor = argb(DEFAULT_GLINT_COLOR)

    @Accordion(title = "Held Item Glint")
    var heldItemOptions = GlintOptions()

    @Accordion(title = "Gui Item Glint")
    var guiItemOptions = GlintOptions()

    @Accordion(title = "Dropped Item Glint")
    var droppedItemOptions = GlintOptions()

    @Accordion(title = "Framed Item Glint")
    var framedItemOptions = GlintOptions()

    @Accordion(title = "Shiny Pots")
    var shinyPotsOptions = GlintOptions.ShinyPots()

}