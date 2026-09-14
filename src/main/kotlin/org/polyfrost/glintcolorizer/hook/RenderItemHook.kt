package org.polyfrost.glintcolorizer.hook

import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.init.Items
import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.glintcolorizer.config.GlintEffectOptions
import java.util.*

object RenderItemHook {

    var itemStack: ItemStack? = null

    var transformType: ItemCameraTransforms.TransformType? = null

    private val playerTransforms: EnumSet<ItemCameraTransforms.TransformType?> = EnumSet.of(
        ItemCameraTransforms.TransformType.FIRST_PERSON,
        ItemCameraTransforms.TransformType.THIRD_PERSON
    )

    private val potionColors = HashMap<Int, Int>()

    fun shouldSkipGlintRendering(): Boolean {
        return !isPotionGlintEnabled || !isRenderingInGUI || !isPotionItem
    }

    val isPotionGlintEnabled: Boolean
        get() = GlintConfig.potionGlint && GlintConfig.enabled

    val isPotionItem: Boolean
        get() = itemStack?.let { it.item is ItemPotion && it.hasEffect() } ?: false

    val isRenderingHeld: Boolean
        get() = playerTransforms.contains(transformType)

    val isRenderingInGUI: Boolean
        get() = transformType == ItemCameraTransforms.TransformType.GUI

    val isRenderingDropped: Boolean
        get() = transformType == ItemCameraTransforms.TransformType.GROUND

    val isRenderingFramed: Boolean
        get() = transformType == ItemCameraTransforms.TransformType.FIXED

    val isShinyPotion: Boolean
        get() = GlintConfig.enabled && GlintConfig.potionGlintBackground && isRenderingInGUI && isPotionItem

    val isPotionColorBased: Boolean
        get() = GlintConfig.enabled && GlintConfig.potionBasedColor && isRenderingInGUI && isPotionItem

    val shouldRenderFullSlot: Boolean
        get() = isPotionGlintEnabled && GlintConfig.potionGlintSize && isRenderingInGUI && isPotionItem

    val activeOptions: GlintEffectOptions?
        get() = when {
            isRenderingHeld -> GlintConfig.heldItem
            isRenderingInGUI -> if (isShinyPotion) GlintConfig.shinyPots else GlintConfig.guiItem
            isRenderingDropped -> GlintConfig.droppedItem
            isRenderingFramed -> GlintConfig.framedItem
            else -> null
        }

    fun glintColor(settings: GlintEffectOptions, isFirstStroke: Boolean): Int {
        val stack = itemStack
        if (stack != null && isPotionColorBased) {
            return getPotionColor(stack)
        }
        return if (settings.individualStrokes) {
            if (isFirstStroke) settings.strokeOneColor.argb else settings.strokeTwoColor.argb
        } else {
            settings.glintColor.argb
        }
    }

    /**
     * <a href="https://github.com/RoccoDev/ShinyPots-1.8">Adapted from ShinyPots by RoccoDev under the LGPL-3.0 license.</a>
     */
    fun getPotionColor(stack: ItemStack): Int = potionColors.getOrPut(stack.metadata) {
        Items.potionitem.getColorFromItemStack(stack, 0) or 0xFF000000.toInt()
    }

}
