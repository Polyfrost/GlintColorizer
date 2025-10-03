package org.polyfrost.glintcolorizer.config

import org.polyfrost.oneconfig.api.config.v1.annotations.Checkbox
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch

open class ShinyPots : BaseGlint() {
    @Checkbox(title = "Render Over Full Slot")
    var useFullSlotShine = false

    @Checkbox(title = "Custom Shiny Effect Color")
    var useCustomColor = false

    @Switch(title = "Potion Color Based Glint")
    var usePotionBasedColor = false
}