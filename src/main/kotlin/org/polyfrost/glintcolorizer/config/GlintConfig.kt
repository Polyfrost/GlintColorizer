package org.polyfrost.glintcolorizer.config

import org.polyfrost.glintcolorizer.GlintColorizerConstants
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Accordion
import org.polyfrost.oneconfig.api.config.v1.annotations.Button
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.polyui.color.argb

object GlintConfig : Config(
    "${GlintColorizerConstants.ID}.json",
    "/assets/${GlintColorizerConstants.ID}/glintcolorizer_dark.svg",
    GlintColorizerConstants.NAME,
    Category.QOL
) {
    const val DEFAULT_GLINT_COLOR = -8372020
    const val OLD_GLINT_COLOR = -10407781

    @Switch(
        title = "Use Custom Renderer",
        category = "Global",
        subcategory = "Configuration",
        description = "Replaces the vanilla glint renderer with a custom one to allow customizability."
    )
    var useCustomRenderer = true

    @Button(
        title = "1.7 Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies the 1.7 glint color to all transform types."
    )
    fun applyOldGlint() {
        armorOptions.glintColor = argb(OLD_GLINT_COLOR)
        heldItemOptions.glintColor = argb(OLD_GLINT_COLOR)
        /* GUI Items' glint color are actually the default 1.8 glint color! */
        droppedItemOptions.glintColor = argb(OLD_GLINT_COLOR)
        framedItemOptions.glintColor = argb(OLD_GLINT_COLOR)
        shinyPotsOptions.glintColor = argb(OLD_GLINT_COLOR)
    }

    @Accordion(title = "Armor Glint", index = 0)
    var armorOptions = ArmorGlint()

    @Accordion(title = "Held Item Glint", index = 1)
    var heldItemOptions = BaseGlint()

    @Accordion(title = "Gui Item Glint", index = 2)
    var guiItemOptions = BaseGlint()

    @Accordion(title = "Framed Item Glint", index = 3)
    var framedItemOptions = BaseGlint()

    @Accordion(title = "Dropped Item Glint", index = 4)
    var droppedItemOptions = BaseGlint()

    @Accordion(title = "Shiny Pots", index = 5)
    var shinyPotsOptions = ShinyPots()

    init {
        hideIf("useCustomRenderer") {
            //#if MC < 1.17
            true
            //#else
            //$$false
            //#endif
        }
        armorOptions.firstStrokeRotation = 0F
        armorOptions.secondStrokeRotation = 60F
    }
}
