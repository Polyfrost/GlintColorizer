package org.polyfrost.glintcolorizer

import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.glintcolorizer.config.GlintOptions

object GlintMetadata {
    @JvmStatic
    fun setupWithItem(renderingItem: ItemStack?) {
        if (renderingItem == null) {
            renderingItemMetadata = 0
            return
        }
        renderingItemMetadata = renderingItem.metadata
        if (renderingItem.item is ItemPotion && renderingItem.hasEffect() && GlintConfig.shinyPotsOptions.usePotionGlint) {
            renderMode = RenderMode.SHINY
        }
    }

    @JvmStatic
    var renderingItemMetadata = 0

    @JvmStatic
    var renderMode = RenderMode.HELD
        set(value) {
            if (value == RenderMode.GUI && field == RenderMode.SHINY) {
                // preserve shiny render mode !
                return
            }
            field = value
        }

    enum class RenderMode {
        HELD,
        SHINY,
        GUI,
        DROPPED,
        FRAMED;
    }

    @JvmStatic
    val renderingOptions: GlintOptions
        get() = when (renderMode) {
            RenderMode.HELD -> GlintConfig.heldItemOptions
            RenderMode.SHINY -> GlintConfig.shinyPotsOptions
            RenderMode.GUI -> GlintConfig.guiItemOptions
            RenderMode.DROPPED -> GlintConfig.droppedItemOptions
            RenderMode.FRAMED -> GlintConfig.framedItemOptions
        }

}
