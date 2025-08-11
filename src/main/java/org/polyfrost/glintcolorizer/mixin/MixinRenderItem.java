package org.polyfrost.glintcolorizer.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.RenderItem;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {
    //#if MC <= 1.12.2
    // Default
    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V", ordinal = 0), index = 1)
    private int glintcolorizer$modify1stStrokeColor(int color) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getColor(true);
        } else {
            return color;
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
    private float glintcolorizer$modify1stStrokeRotation(float angle) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getRenderingOptions().getFirstStrokeRotation();
        } else {
            return angle;
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V", ordinal = 1), index = 1)
    private int glintcolorizer$modify2ndStrokeColor(int color) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getColor(false);
        } else {
            return color;
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float glintcolorizer$modify2ndStrokeRotation(float angle) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getRenderingOptions().getSecondStrokeRotation();
        } else {
            return angle;
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"), index = 0)
    private float glintcolorizer$modifySpeed(float speed) {
        if (GlintConfig.INSTANCE.enabled) {
            speed *= GlintMetadata.getRenderingOptions().getSpeed();
        }

        return speed;
    }

    @WrapOperation(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void glintcolorizer$modifyScale(float x, float y, float z, Operation<Void> original) {
        if (GlintConfig.INSTANCE.enabled) {
            final float scale = GlintMetadata.getRenderingOptions().getScale();
            x *= scale;
            y *= scale;
            z *= scale;
        }

        original.call(x, y, z);
    }

    // Shiny Potions
    // @Shadow
    // protected abstract void renderEffect(IBakedModel model);

    // @Shadow
    // @Final
    // private TextureManager textureManager;

    // @Accessor("RES_ITEM_GLINT")
    // private @NotNull ResourceLocation getResItemGlint() {
    //     throw new UnsupportedOperationException();
    // }

    // @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At("HEAD"))
    // private void glintcolorizer$setupRenderMetadata(ItemStack stack, IBakedModel model, CallbackInfo ci) {
    //     GlintMetadata.setupWithItem(stack);
    // }

    // @WrapWithCondition(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    // private boolean glintcolorizer$disableDepthFunction(int factor) {
    //     // this is the normal code, which we execute if we aren't rendering a shiny potion
    //     return GlintMetadata.getRenderMode() != GlintMetadata.RenderMode.SHINY;
    // }

    // @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    // private void glintcolorizer$onRenderItemFirstPass(ItemStack stack, IBakedModel model, CallbackInfo ci) {
    //     if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
    //         GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
    //         if (options.getDisablePotionGlint() || options.getUseCustomColor()) {
    //             renderEffect(model);
    //         }
    //     }
    // }

    // @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER))
    // private void glintcolorizer$onRenderItemSecondPass(ItemStack stack, IBakedModel model, CallbackInfo ci) {
    //     if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
    //         GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
    //         if (options.getUseCustomColor() && !options.getDisablePotionGlint()) {
    //             GlintRendererKt.renderEffect((RenderItem) (Object) this, model, textureManager, getResItemGlint());
    //         }
    //     }
    // }

    // @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"))
    // private boolean glintcolorizer$disableEnchantedEffect(ItemStack instance, Operation<Boolean> original) {
    //     if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
    //         GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
    //         return !options.getDisablePotionGlint() && !options.getUseCustomColor();
    //     } else {
    //         return original.call(instance);
    //     }
    // }

    // @Inject(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
    // private void glintcolorizer$enableFullSlotSize(IBakedModel model, CallbackInfo ci) {
    //     if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
    //         GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
    //         if (options.getUseFullSlotShine()) {
    //             GlStateManager.scale(1.25, 1.25, 1.25);
    //             GlStateManager.translate(-0.1, -0.1, 0.0);
    //         }
    //     }
    // }
    //#endif
}
