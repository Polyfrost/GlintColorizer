@file:JvmName("SecondGlintHandler")

package org.polyfrost.glintcolorizer.handler

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.util.ResourceLocation
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.glintcolorizer.config.GlintEffectOptions
import org.polyfrost.glintcolorizer.hook.RenderItemHook
import org.polyfrost.glintcolorizer.mixin.accessor.RenderItemAccessor

fun renderEffect(renderItem : RenderItem, model : IBakedModel, textureManager : TextureManager, glintResource : ResourceLocation) {
    val settings = RenderItemHook.activeOptions ?: GlintConfig.guiItem
    GlStateManager.pushMatrix()
    GlStateManager.depthMask(false)
    GlStateManager.depthFunc(514)
    GlStateManager.disableLighting()
    GlStateManager.blendFunc(768, 1)
    if (RenderItemHook.shouldRenderFullSlot) {
        GlStateManager.scale(1.25, 1.25, 1.25)
        GlStateManager.translate(-0.1, -0.1, 0.0)
    }
    textureManager.bindTexture(glintResource)
    GlStateManager.matrixMode(5890)
    glintStroke1(renderItem, model, settings)
    glintStroke2(renderItem, model, settings)
    GlStateManager.matrixMode(5888)
    GlStateManager.blendFunc(770, 771)
    GlStateManager.enableLighting()
    GlStateManager.depthFunc(515)
    GlStateManager.depthMask(true)
    textureManager.bindTexture(TextureMap.locationBlocksTexture)
    GlStateManager.popMatrix()
}

fun glintStroke1(renderItem : RenderItem, model : IBakedModel, settings : GlintEffectOptions) {
    GlStateManager.pushMatrix()
    val scale = 8.0f * settings.scale
    GlStateManager.scale(scale, scale, scale)
    val f = (scaledTime(settings) % 3000L).toFloat() / 3000.0f / 8.0f
    GlStateManager.translate(f, 0.0f, 0.0f)
    GlStateManager.rotate(settings.strokeRotOne, 0.0f, 0.0f, 1.0f)
    (renderItem as RenderItemAccessor).invokeRenderModel(model, RenderItemHook.glintColor(settings, true))
    GlStateManager.popMatrix()
}

fun glintStroke2(renderItem : RenderItem, model : IBakedModel, settings : GlintEffectOptions) {
    GlStateManager.pushMatrix()
    val scale = 8.0f * settings.scale
    GlStateManager.scale(scale, scale, scale)
    val f1 = (scaledTime(settings) % 4873L).toFloat() / 4873.0f / 8.0f
    GlStateManager.translate(-f1, 0.0f, 0.0f)
    GlStateManager.rotate(settings.strokeRotTwo, 0.0f, 0.0f, 1.0f)
    (renderItem as RenderItemAccessor).invokeRenderModel(model, RenderItemHook.glintColor(settings, false))
    GlStateManager.popMatrix()
}

private fun scaledTime(settings : GlintEffectOptions) : Long =
    (Minecraft.getSystemTime() * settings.speed.toDouble()).toLong()
