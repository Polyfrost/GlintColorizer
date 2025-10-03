package org.polyfrost.glintcolorizer

import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import org.polyfrost.glintcolorizer.config.BaseGlint
import org.polyfrost.glintcolorizer.config.GlintConfig

object GlintMetadata {
    private val EMPTY =
        //#if MC <=1.12.2
        ItemStack(net.minecraft.init.Blocks.air)
        //#else
        //$$ItemStack.EMPTY
        //#endif

    enum class RenderMode {
        HELD,
        SHINY,
        GUI,
        DROPPED,
        FRAMED
    }

    private var renderingItemCached: ItemStack = EMPTY

    @JvmStatic
    var renderMode = RenderMode.HELD
        set(value) {
            if (!(value == RenderMode.GUI && field == RenderMode.SHINY)) {
                field = value // preserve shiny render mode !
            }
        }

    @JvmStatic
    fun setItemStack(itemStack: ItemStack?) {
        var tempStack = itemStack
        if (tempStack == null) {
            tempStack = EMPTY
        }

        if (!ItemStack.areItemStacksEqual(tempStack, renderingItemCached)) {
            renderingItemCached = tempStack
        }
    }

    @JvmStatic
    fun getRenderingOptions(): BaseGlint {
        return when (renderMode) {
            RenderMode.HELD -> GlintConfig.heldItemOptions
            RenderMode.SHINY -> GlintConfig.shinyPotsOptions
            RenderMode.GUI -> GlintConfig.guiItemOptions
            RenderMode.DROPPED -> GlintConfig.droppedItemOptions
            RenderMode.FRAMED -> GlintConfig.framedItemOptions
        }
    }

    @JvmStatic
    fun getGlintColor(layer: GlintLayer, isArmor: Boolean): Int {
        var options = if (isArmor) GlintConfig.armorOptions else getRenderingOptions()
        if (renderingItemCached.item is ItemPotion && GlintConfig.shinyPotsOptions.useCustomColor) {
            options = GlintConfig.shinyPotsOptions
            if (options.usePotionBasedColor) {
                //#if MC <=1.21.5
                //$$
                //#if MC <= 1.12.2
                //$$return net.minecraft.potion.PotionUtil.getColor(renderingItemCached!!) or -0x1000000
                //#elseif MC <=1.8.9
                return net.minecraft.potion.PotionHelper.getLiquidColor(
                    renderingItemCached.metadata,
                    false
                ) or -0x1000000
                //#endif
            }
        }

        if (options.individualStrokes) {
            if (layer == GlintLayer.FIRST) {
                return options.strokeOneColor.rgba;
            } else if (layer == GlintLayer.SECOND) {
                return options.strokeTwoColor.rgba;
            }
        }

        return options.glintColor.rgba
    }
}
