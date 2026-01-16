package org.polyfrost.glintcolorizer.mixins;

import org.polyfrost.glintcolorizer.util.GlintMetadata;
import org.polyfrost.glintcolorizer.util.GlintPipeline;
import org.polyfrost.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=1.21.6 {
import net.minecraft.client.renderer.block.model.BakedQuad;
import java.util.List;
//?} else {
/*import net.minecraft.client.resources.model.BakedModel;
*///?}

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer_ApplyCustomRenderTypes {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private static void glintcolorizer$storeDisplayType(
			final ItemDisplayContext displayContext,
			final PoseStack poseStack,
			final MultiBufferSource bufferSource,
			final int packedLight,
			final int packedOverlay,
			final int[] tintLayers,
			//? <1.21.6
			/*final BakedModel model,*/
			//? >=1.21.6
			final List<BakedQuad> list,
			final RenderType renderType,
			final ItemStackRenderState.FoilType foilType,
			final CallbackInfo ci
	) {
        GlintMetadata.setRenderMode(switch (displayContext) {
            case FIRST_PERSON_RIGHT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> GlintMetadata.RenderMode.HELD;
            case FIXED -> GlintMetadata.RenderMode.FRAMED;
            case GROUND -> GlintMetadata.RenderMode.DROPPED;
            default -> GlintMetadata.RenderMode.GUI;
        });
    }

    @Inject(method = "getFoilBuffer", at = @At("RETURN"), cancellable = true)
    private static void glintcolorizer$replaceWithCustomRenderer$default(MultiBufferSource multiBufferSource, RenderType itemRenderType, boolean isEntity, boolean hasFoil, CallbackInfoReturnable<VertexConsumer> cir) {
        if (GlintColorizerConfig.enabled && hasFoil) {
            final VertexConsumer itemVertexConsumer = multiBufferSource.getBuffer(itemRenderType);
            if (GlintMetadata.getRenderingOptions().enabled) {
                final boolean shiny = GlintMetadata.getItemStack().getItem() instanceof PotionItem && GlintColorizerConfig.shinyPots.fullSlotShine && GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.GUI;
                final VertexConsumer firstGlintLayerVertexConsumer = multiBufferSource.getBuffer(shiny ? GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE : GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE);
                final VertexConsumer secondGlintLayerVertexConsumer = multiBufferSource.getBuffer(shiny ? GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE : GlintPipeline.ITEM_GLINT_2ND_LAYER_RENDERTYPE);
                cir.setReturnValue(VertexMultiConsumer.create(firstGlintLayerVertexConsumer, VertexMultiConsumer.create(secondGlintLayerVertexConsumer, itemVertexConsumer)));
            } else {
                cir.setReturnValue(itemVertexConsumer);
            }
        }
    }

    // TODO 1.21.9 EquipmentLayerRenderer
    //? <=1.21.8 {
    /*@Inject(method = "getArmorFoilBuffer", at = @At("RETURN"), cancellable = true)
    private static void glintcolorizer$replaceWithCustomRenderer$armor(MultiBufferSource multiBufferSource, RenderType armorRenderType, boolean hasFoil, CallbackInfoReturnable<VertexConsumer> cir) {
        if (GlintColorizerConfig.enabled && hasFoil) {
            final VertexConsumer armorVertexConsumer = multiBufferSource.getBuffer(armorRenderType);
            if (GlintColorizerConfig.armorGlint.enabled) {
                final VertexConsumer firstGlintLayerVertexConsumer = multiBufferSource.getBuffer(GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE);
                final VertexConsumer secondGlintLayerVertexConsumer = multiBufferSource.getBuffer(GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE);
                cir.setReturnValue(VertexMultiConsumer.create(firstGlintLayerVertexConsumer, VertexMultiConsumer.create(secondGlintLayerVertexConsumer, armorVertexConsumer)));
            } else {
                cir.setReturnValue(armorVertexConsumer);
            }
        }
    }
    *///?}

    @WrapOperation(method =
            //? if >=1.21.6 {
            "getSpecialFoilBuffer",
            //?} else {
            /*"getCompassFoilBuffer",
            *///?}
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0))
    private static VertexConsumer glintcolorizer$replaceWithCustomRenderer$compass(MultiBufferSource multiBufferSource, RenderType renderType, Operation<VertexConsumer> original) {
        if (GlintColorizerConfig.enabled) {
            final VertexConsumer firstGlintLayerVertexConsumer = multiBufferSource.getBuffer(GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE);
            final VertexConsumer secondGlintLayerVertexConsumer = multiBufferSource.getBuffer(GlintPipeline.ITEM_GLINT_2ND_LAYER_RENDERTYPE);
            return VertexMultiConsumer.create(firstGlintLayerVertexConsumer, secondGlintLayerVertexConsumer);
        } else {
            return original.call(multiBufferSource, renderType);
        }
    }

    @WrapOperation(method =
            //? if >=1.21.6 {
            "getSpecialFoilBuffer",
            //?} else {
            /*"getCompassFoilBuffer",
            *///?}
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexMultiConsumer;create(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/VertexConsumer;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0))
    private static VertexConsumer glintcolorizer$replaceWithCustomRenderer$compass$enabled(VertexConsumer glintVertexConsumer, VertexConsumer itemVertexConsumer, Operation<VertexConsumer> original) {
        if (GlintColorizerConfig.enabled && !GlintMetadata.getRenderingOptions().enabled) {
            return itemVertexConsumer;
        } else {
            return original.call(glintVertexConsumer, itemVertexConsumer);
        }
    }
}
