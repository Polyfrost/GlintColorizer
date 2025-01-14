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
import org.polyfrost.glintcolorizer.mixin.accessor.RenderItemAccessor

fun renderEffect(renderItem : RenderItem, model : IBakedModel, textureManager : TextureManager, glintResource : ResourceLocation) {
    GlStateManager.pushMatrix()
    GlStateManager.depthMask(false)
    GlStateManager.depthFunc(514)
    GlStateManager.disableLighting()
    GlStateManager.blendFunc(768, 1)
    textureManager.bindTexture(glintResource)
    GlStateManager.matrixMode(5890)
    glintStroke1(renderItem, model)
    glintStroke2(renderItem, model)
    GlStateManager.matrixMode(5888)
    GlStateManager.blendFunc(770, 771)
    GlStateManager.enableLighting()
    GlStateManager.depthFunc(515)
    GlStateManager.depthMask(true)
    textureManager.bindTexture(TextureMap.locationBlocksTexture)
    GlStateManager.popMatrix()
}

fun glintStroke1(renderItem : RenderItem, model : IBakedModel) {
    GlStateManager.pushMatrix()
    GlStateManager.scale(8.0f, 8.0f, 8.0f)
    val f = (Minecraft.getSystemTime() % 3000L).toFloat() / 3000.0f / 8.0f
    GlStateManager.translate(f, 0.0f, 0.0f)
    GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f)
    (renderItem as RenderItemAccessor).invokeRenderModel(model,
        if (GlintConfig.guiItem.individualStrokes) GlintConfig.guiItem.strokeOneColor.rgba else GlintConfig.guiItem.glintColor.rgba
    )
    GlStateManager.popMatrix()
}

fun glintStroke2(renderItem : RenderItem, model : IBakedModel) {
    GlStateManager.pushMatrix()
    GlStateManager.scale(8.0f, 8.0f, 8.0f)
    val f1 = (Minecraft.getSystemTime() % 4873L).toFloat() / 4873.0f / 8.0f
    GlStateManager.translate(-f1, 0.0f, 0.0f)
    GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f)
    (renderItem as RenderItemAccessor).invokeRenderModel(model,
        if (GlintConfig.guiItem.individualStrokes) GlintConfig.guiItem.strokeTwoColor.rgba else GlintConfig.guiItem.glintColor.rgba
    )
    GlStateManager.popMatrix()
}