@file:JvmName("SecondGlintHandler")

package org.polyfrost.glintcolorizer

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.glintcolorizer.mixin.accessor.RenderModelAccessor

fun renderEffect(
    renderItem: RenderItem,
    model: IBakedModel,
    textureManager: TextureManager,
    glintResource: ResourceLocation,
) {
    GlStateManager.pushMatrix()
    GlStateManager.depthMask(false)
    GlStateManager.depthFunc(GL11.GL_EQUAL)
    GlStateManager.disableLighting()
    GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE)
    textureManager.bindTexture(glintResource)
    GlStateManager.matrixMode(GL11.GL_TEXTURE)
    renderGlintStroke1(renderItem, model)
    renderGlintStroke2(renderItem, model)
    GlStateManager.matrixMode(GL11.GL_MODELVIEW)
    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GlStateManager.enableLighting()
    GlStateManager.depthFunc(GL11.GL_LEQUAL)
    GlStateManager.depthMask(true)
    textureManager.bindTexture(TextureMap.locationBlocksTexture)
    GlStateManager.popMatrix()
}

fun renderGlintStroke1(renderItem: RenderItem, model: IBakedModel) {
    val options = GlintConfig.guiItemOptions
    GlStateManager.pushMatrix()
    GlStateManager.scale(8.0f, 8.0f, 8.0f)
    val f = (Minecraft.getSystemTime() % 3000L).toFloat() / 3000.0f / 8.0f
    GlStateManager.translate(f, 0.0f, 0.0f)
    GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f)
    (renderItem as RenderModelAccessor).`glintcolorizer$renderModel`(
        model,
        if (options.individualStrokes)
            options.strokeOneColor.rgba
        else
            options.glintColor.rgba
    )
    GlStateManager.popMatrix()
}

fun renderGlintStroke2(renderItem: RenderItem, model: IBakedModel) {
    val options = GlintConfig.guiItemOptions
    GlStateManager.pushMatrix()
    GlStateManager.scale(8.0f, 8.0f, 8.0f)
    val f1 = (Minecraft.getSystemTime() % 4873L).toFloat() / 4873.0f / 8.0f
    GlStateManager.translate(-f1, 0.0f, 0.0f)
    GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f)
    (renderItem as RenderModelAccessor).`glintcolorizer$renderModel`(
        model,
        if (options.individualStrokes)
            options.strokeTwoColor.rgba
        else
            options.glintColor.rgba
    )
    GlStateManager.popMatrix()
}