package org.polyfrost.glintcolorizer.mixins;

import org.polyfrost.glintcolorizer.util.GlintLayer;
import org.polyfrost.glintcolorizer.util.GlintMetadata;
import org.polyfrost.glintcolorizer.util.GlintPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? >=1.21.8 {
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderPass;
import org.lwjgl.system.MemoryStack;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
//?} else {
/*import net.minecraft.client.renderer.CompiledShaderProgram;
import com.mojang.blaze3d.systems.RenderSystem;
*///?}

@Mixin(
		//? >=1.21.8 {
		RenderType.CompositeRenderType.class
		//?} else {
		/*RenderType.class
		*///?}
)
public abstract class MixinRenderType_ApplyGlintColor {
	//? >=1.21.8 {
	@Inject(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setIndexBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;)V", shift = At.Shift.AFTER))
	private void glintcolorizer$applyShaderSettings(final MeshData meshData, final CallbackInfo ci, @Local(name = "renderPass") RenderPass renderPass) {
		GpuBuffer buffer = null;
		try (final MemoryStack stack = MemoryStack.stackPush()) {
			final Std140Builder builder = Std140Builder.onStack(stack, 4);
			builder.putInt(GlintMetadata.getGlintColor(glintcolorizer$getLayer(), glintcolorizer$isArmor()));
			buffer = RenderSystem.getDevice().createBuffer(() -> "Custom Glint Color", GpuBuffer.USAGE_UNIFORM, builder.get());
		} catch (Exception ignored) {
		}

		if (buffer != null) {
			renderPass.setUniform("Glint", buffer);
		}
	}
	//?} else {
	/*@Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;setupRenderState()V", shift = At.Shift.AFTER))
	private void glintcolorizer$applyShaderSettings(final MeshData meshData, final CallbackInfo ci) {
		final CompiledShaderProgram shaderProgram = RenderSystem.getShader();
		if (shaderProgram != null) {
			shaderProgram.safeGetUniform("GlintColor").set(GlintMetadata.getGlintColor(glintcolorizer$getLayer(), glintcolorizer$isArmor()));
		}
	}
	*///?}

	@Unique
	private boolean glintcolorizer$isArmor() {
		final RenderType thiz = (RenderType) (Object) this;
		return thiz == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE
				|| thiz == GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE;
	}

	@Unique
	private GlintLayer glintcolorizer$getLayer() {
		final RenderType thiz = (RenderType) (Object) this;
		if (thiz == GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE
				|| thiz == GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE
				|| thiz == GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE) {
			return GlintLayer.FIRST;
		} else {
			return GlintLayer.SECOND;
		}
	}
}