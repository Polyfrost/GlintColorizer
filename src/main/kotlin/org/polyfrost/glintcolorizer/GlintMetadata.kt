package org.polyfrost.glintcolorizer

import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionHelper
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.glintcolorizer.config.GlintOptions
import org.polyfrost.glintcolorizer.config.GlintOptions.ShinyPots

object GlintMetadata {
    private var renderingItemCached: ItemStack? = null

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
    fun getRenderingOptions(): GlintOptions {
        return when (renderMode) {
            RenderMode.HELD -> GlintConfig.heldItemOptions
            RenderMode.SHINY -> GlintConfig.shinyPotsOptions
            RenderMode.GUI -> GlintConfig.guiItemOptions
            RenderMode.DROPPED -> GlintConfig.droppedItemOptions
            RenderMode.FRAMED -> GlintConfig.framedItemOptions
        }
    }

    @JvmStatic
    fun setupWithItem(renderingItem: ItemStack?) {
        if (renderingItem == null) {
            renderingItemCached = null
        } else {
            renderingItemCached = renderingItem
            if (renderingItem.item is ItemPotion && renderingItem.hasEffect() && GlintConfig.shinyPotsOptions.usePotionGlint) {
                renderMode = RenderMode.SHINY
            }
        }
    }

    @JvmStatic
    fun getColor(firstStroke: Boolean): Int {
        val options = getRenderingOptions()
        return if (options is ShinyPots && options.usePotionBasedColor && renderingItemCached != null) {
            //#if MC > 1.8.9
            //$$ net.minecraft.potion.PotionUtil.getColor(renderingItemCached!!) or -0x1000000
            //#else
            PotionHelper.getLiquidColor(renderingItemCached!!.metadata, false) or -0x1000000
            //#endif
        } else {
            if (options.individualStrokes)
                if (firstStroke)
                    options.strokeOneColor.argb
                else
                    options.strokeTwoColor.argb
            else
                options.glintColor.argb
        }
    }

    enum class RenderMode {
        HELD,
        SHINY,
        GUI,
        DROPPED,
        FRAMED
    }
}
