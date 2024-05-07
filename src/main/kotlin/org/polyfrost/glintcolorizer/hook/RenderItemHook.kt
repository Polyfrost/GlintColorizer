package org.polyfrost.glintcolorizer.hook

import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import org.polyfrost.glintcolorizer.config.GlintConfig
import java.util.*

object RenderItemHook {

    var itemStack: ItemStack? = null

    var transformType: ItemCameraTransforms.TransformType? = null

    private val playerTransforms: EnumSet<ItemCameraTransforms.TransformType?> = EnumSet.of(
        ItemCameraTransforms.TransformType.FIRST_PERSON,
        ItemCameraTransforms.TransformType.THIRD_PERSON
    )

    fun shouldSkipGlintRendering(): Boolean {
        return !isPotionGlintEnabled || !isRenderingInGUI || !isPotionItem
    }

    val isPotionGlintEnabled: Boolean
        get() = GlintConfig.potionGlint && GlintConfig.enabled

    val isPotionItem: Boolean
        get() = itemStack!!.item is ItemPotion && itemStack!!.hasEffect()

    val isRenderingHeld: Boolean
        get() = playerTransforms.contains(transformType)

    val isRenderingInGUI: Boolean
        get() = transformType == ItemCameraTransforms.TransformType.GUI

    val isRenderingDropped: Boolean
        get() = transformType == ItemCameraTransforms.TransformType.GROUND

    val isRenderingFramed: Boolean
        get() = transformType == ItemCameraTransforms.TransformType.FIXED

}
