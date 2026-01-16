package org.polyfrost.glintcolorizer.mixins.v1_21_9;

import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.9 {
import org.polyfrost.glintcolorizer.config.GlintColorizerConfig;
import org.polyfrost.glintcolorizer.util.GlintPipeline;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentLayerRenderer.class)
public abstract class MixinEquipmentLayerRenderer_ApplyArmorGlint {
	@WrapOperation(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/ResourceLocation;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", ordinal = 1))
	private <S> void glintcolorizer$armorGlint(final OrderedSubmitNodeCollector instance, final Model<? super S> model, final S renderState, final PoseStack poseStack, final RenderType renderType, final int packedLight, final int overlayUv, final int color, final TextureAtlasSprite textureAtlasSprite, final int light, final ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, final Operation<Void> original) {
		if (GlintColorizerConfig.enabled) {
			if (!GlintColorizerConfig.armorGlint.enabled) {
				return;
			}

			original.call(instance, model, renderState, poseStack, GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE, packedLight, overlayUv, color, textureAtlasSprite, light, crumblingOverlay);
			original.call(instance, model, renderState, poseStack, GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE, packedLight, overlayUv, color, textureAtlasSprite, light, crumblingOverlay);
			return;
		}

		original.call(instance, model, renderState, poseStack, renderType, packedLight, overlayUv, color, textureAtlasSprite, light, crumblingOverlay);
	}
}
//?} else {
/*@Mixin(net.minecraft.client.Minecraft.class)
public abstract class MixinEquipmentLayerRenderer_ApplyArmorGlint {}
*///?}
