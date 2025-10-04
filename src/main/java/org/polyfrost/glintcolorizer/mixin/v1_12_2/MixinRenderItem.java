package org.polyfrost.glintcolorizer.mixin.v1_12_2;

import net.minecraft.client.renderer.entity.RenderItem;
import org.spongepowered.asm.mixin.Mixin;

//#if MC <= 1.12.2
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.polyfrost.glintcolorizer.GlintLayer;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.config.ShinyPots;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
//#endif

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {
    //#if MC <= 1.12.2
    // Default
    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V", ordinal = 0), index = 1)
    private int glintcolorizer$modify1stStrokeColor(int color) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getGlintColor(GlintLayer.FIRST, false);
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
            return GlintMetadata.getGlintColor(GlintLayer.SECOND, false);
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
    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At("HEAD"))
    private void glintcolorizer$setupRenderMetadata(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        GlintMetadata.setItemStack(stack);
    }

    @WrapWithCondition(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private boolean glintcolorizer$disableDepthFunction(int factor) {
        // this is the normal code, which we execute if we aren't rendering a shiny potion
        return GlintMetadata.getRenderMode() != GlintMetadata.RenderMode.SHINY;
    }

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

    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"))
    private boolean glintcolorizer$disableEnchantedEffect(ItemStack instance, Operation<Boolean> original) {
        if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
            ShinyPots options = (ShinyPots) GlintMetadata.getRenderingOptions();
            return !options.getEnabled() && !options.getUseCustomColor();
        } else {
            return original.call(instance);
        }
    }

    @Inject(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
    private void glintcolorizer$enableFullSlotSize(IBakedModel model, CallbackInfo ci) {
        if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
            ShinyPots options = (ShinyPots) GlintMetadata.getRenderingOptions();
            if (options.getUseFullSlotShine()) {
                GlStateManager.scale(1.25, 1.25, 1.25);
                GlStateManager.translate(-0.1, -0.1, 0.0);
            }
        }
    }
    //#endif
}
