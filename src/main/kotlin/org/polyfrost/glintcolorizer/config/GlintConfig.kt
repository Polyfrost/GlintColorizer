package org.polyfrost.glintcolorizer.config

import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.Properties
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.internal.ui.components.settings.bumpResetEpoch

object GlintConfig : Config(
    "glintcolorizer.json", "/assets/glintcolorizer/glintcolorizer_dark.svg", "GlintColorizer", Category.VISUALS
) {
    const val DEFAULT_COLOR = -8372020
    const val OLD_GLINT_COLOR = -10407781

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
        shinyPots.reset()
        refreshOptions()
    }

    @Color(
        title = "Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        description = "Modifies the color of the enchantment glint."
    )
    var globalColor = PolyColor(DEFAULT_COLOR)

    @Button(
        title = "Apply Global Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies your global glint color. Resets ALL custom colors."
    )
    fun applyColors() {
        heldItem.glintColor.copyFrom(globalColor)
        guiItem.glintColor.copyFrom(globalColor)
        droppedItem.glintColor.copyFrom(globalColor)
        framedItem.glintColor.copyFrom(globalColor)
        shinyPots.glintColor.copyFrom(globalColor)
        armorColor.copyFrom(globalColor)

        heldItem.individualStrokes = false
        guiItem.individualStrokes = false
        droppedItem.individualStrokes = false
        framedItem.individualStrokes = false
        shinyPots.individualStrokes = false
        refreshOptions()
    }

    @Button(
        title = "1.7 Glint Color",
        category = "Global",
        subcategory = "Configuration",
        text = "Apply",
        description = "Applies the 1.7 glint color to all transform types."
    )
    fun oldGlint() {
        heldItem.glintColor.argb = OLD_GLINT_COLOR
        droppedItem.glintColor.argb = OLD_GLINT_COLOR
        framedItem.glintColor.argb = OLD_GLINT_COLOR
        shinyPots.glintColor.argb = OLD_GLINT_COLOR
        refreshOptions()
    }

    private fun PolyColor.copyFrom(other: PolyColor) {
        argb = other.rawArgb
        chroma = other.chroma
        chromaSpeed = other.chromaSpeed
    }

    @Accordion(
        category = "Held Item"
    )
    var heldItem = GlintEffectOptions()

    @Accordion(
        category = "GUI Item"
    )
    var guiItem = GlintEffectOptions()

    @Accordion(
        category = "Dropped Item"
    )
    var droppedItem = GlintEffectOptions()

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
    var armorColor = PolyColor(DEFAULT_COLOR)

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

    private var rebound = false

    override fun initialize(byConfigManager: Boolean) {
        super.initialize(byConfigManager)
        if (rebound) return
        rebound = true
        rebindAccordion("heldItem", heldItem)
        rebindAccordion("guiItem", guiItem)
        rebindAccordion("droppedItem", droppedItem)
        rebindAccordion("framedItem", framedItem)
        rebindAccordion("shinyPots", shinyPots)
    }

    private fun rebindAccordion(id: String, target: GlintEffectOptions) {
        val subTree = getTree()?.get(id) as? Tree ?: return
        for (field in GlintEffectOptions::class.java.declaredFields) {
            if (field.isSynthetic) continue
            if (subTree.get(field.name) !is Property<*>) continue
            subTree.put(Properties.field<Any>(field = field, owner = target))
        }
    }

    private fun refreshOptions() {
        runCatching {
            getTree()?.let { bumpAll(it) }
        }.onFailure {
            println("[GlintColorizer] could not refresh the config UI: $it")
        }
        save()
    }

    private fun bumpAll(tree: Tree) {
        for (node in tree.map.values) {
            when (node) {
                is Property<*> -> bumpResetEpoch(node)
                is Tree -> bumpAll(node)
            }
        }
    }
}
