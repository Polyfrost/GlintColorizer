package org.polyfrost.glintcolorizer.render

//? if modern {
import net.minecraft.world.item.ItemStack

//? if <1.21.6 {
/*import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.texture.OverlayTexture
*///?} else {
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.renderer.RenderPipelines
import org.joml.Matrix3x2f
//?}
//? if >=1.21.6 && <26.1 {
/*import net.minecraft.client.gui.render.state.GuiElementRenderState
import net.minecraft.client.gui.render.state.GuiItemRenderState
import net.minecraft.client.gui.render.state.GuiRenderState
*///?} else if >=26.1 {
import net.minecraft.client.renderer.state.gui.GuiElementRenderState
import net.minecraft.client.renderer.state.gui.GuiItemRenderState
import net.minecraft.client.renderer.state.gui.GuiRenderState
//?}
//? if >=1.21.6 && <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexConsumer
*///?} else if >=26.3 {
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.renderpearl.api.pipeline.RenderPipeline
//?}

private const val SPAN = 1.0f / 32.0f

object ShinyEffect {
    //? if <1.21.6 {
    /*@JvmStatic
    fun draw(poseStack: PoseStack, buffers: MultiBufferSource, stack: ItemStack) {
        if (!GlintContext.drawsShinyEffect(stack)) {
            return
        }
        val half = GlintContext.shinyEffectSize / 32.0f
        val pose = poseStack.last()
        val first = if (GlintContext.shinyEffectOpaque) GlintRenderTypes.SHINY_OPAQUE else GlintRenderTypes.SHINY
        quad(buffers.getBuffer(first), pose, half, GlintContext.shinyLayer(stack, false))
        quad(buffers.getBuffer(GlintRenderTypes.SHINY), pose, half, GlintContext.shinyLayer(stack, true))
    }

    private fun quad(buffer: VertexConsumer, pose: PoseStack.Pose, half: Float, layer: GlintLayer) {
        vertex(buffer, pose, -half, half, 0.0f, 0.0f, layer)
        vertex(buffer, pose, -half, -half, 0.0f, SPAN, layer)
        vertex(buffer, pose, half, -half, SPAN, SPAN, layer)
        vertex(buffer, pose, half, half, SPAN, 0.0f, layer)
    }

    private fun vertex(buffer: VertexConsumer, pose: PoseStack.Pose, x: Float, y: Float, u: Float, v: Float, layer: GlintLayer) {
        buffer.addVertex(pose, x, y, -0.5f)
            .setColor(layer.red, layer.green, layer.blue, 255)
            .setUv(layer.u(u, v), layer.v(u, v))
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(0xF000F0)
            .setNormal(0.0f, 0.0f, 1.0f)
    }
    *///?} else {
    @JvmStatic
    fun submit(state: GuiRenderState, item: GuiItemRenderState, stack: ItemStack) {
        if (!GlintContext.drawsShinyEffect(stack)) {
            return
        }
        val size = GlintContext.shinyEffectSize
        val x0 = item.x() - (size - 16) / 2
        val y0 = item.y() - (size - 16) / 2
        val texture = Minecraft.getInstance().textureManager.getTexture(GlintRenderTypes.TEXTURE)
        //? if <1.21.11 {
        /*val textures = TextureSetup.singleTexture(texture.textureView)
        *///?} else
        val textures = TextureSetup.singleTexture(texture.textureView, texture.sampler)
        val first = if (GlintContext.shinyEffectOpaque) RenderPipelines.GUI_TEXTURED else GlintRenderTypes.GUI_SHINY
        add(state, Square(first, textures, item.pose(), x0, y0, size, GlintContext.shinyLayer(stack, false), item.scissorArea()))
        add(state, Square(GlintRenderTypes.GUI_SHINY, textures, item.pose(), x0, y0, size, GlintContext.shinyLayer(stack, true), item.scissorArea()))
    }

    private fun add(state: GuiRenderState, element: GuiElementRenderState) {
        //? if <26.1 {
        /*state.submitGuiElement(element)
        *///?} else
        state.addGuiElement(element)
    }

    private class Square(
        private val pipeline: RenderPipeline,
        private val textureSetup: TextureSetup,
        private val pose: Matrix3x2f,
        private val x0: Int,
        private val y0: Int,
        private val size: Int,
        private val layer: GlintLayer,
        private val scissorArea: ScreenRectangle?,
    ) : GuiElementRenderState {
        override fun pipeline(): RenderPipeline = pipeline

        override fun textureSetup(): TextureSetup = textureSetup

        override fun scissorArea(): ScreenRectangle? = scissorArea

        //? if <1.21.9 {
        /*override fun buildVertices(buffer: VertexConsumer, z: Float) {
            vertex(buffer, x0, y0, z, 0.0f, 0.0f)
            vertex(buffer, x0, y0 + size, z, 0.0f, SPAN)
            vertex(buffer, x0 + size, y0 + size, z, SPAN, SPAN)
            vertex(buffer, x0 + size, y0, z, SPAN, 0.0f)
        }

        private fun vertex(buffer: VertexConsumer, x: Int, y: Int, z: Float, u: Float, v: Float) {
            buffer.addVertexWith2DPose(pose, x.toFloat(), y.toFloat(), z)
                .setUv(layer.u(u, v), layer.v(u, v))
                .setColor(layer.red, layer.green, layer.blue, 255)
        }
        *///?} else {
        override fun buildVertices(buffer: VertexConsumer) {
            vertex(buffer, x0, y0, 0.0f, 0.0f)
            vertex(buffer, x0, y0 + size, 0.0f, SPAN)
            vertex(buffer, x0 + size, y0 + size, SPAN, SPAN)
            vertex(buffer, x0 + size, y0, SPAN, 0.0f)
        }

        private fun vertex(buffer: VertexConsumer, x: Int, y: Int, u: Float, v: Float) {
            buffer.addVertexWith2DPose(pose, x.toFloat(), y.toFloat())
                .setUv(layer.u(u, v), layer.v(u, v))
                .setColor(layer.red, layer.green, layer.blue, 255)
        }
        //?}

        override fun bounds(): ScreenRectangle? {
            val bounds = ScreenRectangle(x0, y0, size, size).transformMaxBounds(pose)
            return if (scissorArea != null) scissorArea.intersection(bounds) else bounds
        }
    }
    //?}
}
//?}
