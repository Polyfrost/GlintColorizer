package org.polyfrost.glintcolorizer

import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionHelper
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.glintcolorizer.config.GlintOptions
import org.polyfrost.glintcolorizer.config.GlintOptions.ShinyPots

object GlintMetadata {
    @JvmStatic
    fun setupWithItem(renderingItem: ItemStack?) {
        if (renderingItem == null) {
            renderingItemMetadata = 0
        } else {
            renderingItemMetadata = renderingItem.metadata
            if (renderingItem.item is ItemPotion && renderingItem.hasEffect() && GlintConfig.shinyPotsOptions.usePotionGlint) {
                renderMode = RenderMode.SHINY
            }
        }
    }

    @JvmStatic
    fun getColor(firstStroke: Boolean): Int {
        val options = renderingOptions
        if (options is ShinyPots && options.usePotionBasedColor) {
            val potionId = renderingItemMetadata
            return PotionHelper.getLiquidColor(potionId, false) or -0x1000000
        }
        return if (options.individualStrokes)
            if (firstStroke)
                options.strokeOneColor.argb
            else
                options.strokeTwoColor.argb
        else
            options.glintColor.argb
    }

    private var renderingItemMetadata = 0

    @JvmStatic
    var renderMode = RenderMode.HELD
        set(value) {
            if (value == RenderMode.GUI && field == RenderMode.SHINY) {
                // preserve shiny render mode !
                return
            }
            field = value
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

    enum class RenderMode {
        HELD,
        SHINY,
        GUI,
        DROPPED,
        FRAMED
    }
}
