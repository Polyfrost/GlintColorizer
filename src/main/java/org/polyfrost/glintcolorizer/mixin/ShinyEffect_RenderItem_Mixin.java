package org.polyfrost.glintcolorizer.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.polyfrost.glintcolorizer.config.GlintOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class ShinyEffect_RenderItem_Mixin {
    @Shadow
    protected abstract void renderEffect(IBakedModel model);

    @Shadow
    @Final
    private TextureManager textureManager;

    @Accessor("RES_ITEM_GLINT")
    private @NotNull ResourceLocation getResItemGlint() {
        throw new UnsupportedOperationException();
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At("HEAD"))
    private void glintcolorizer$setupRenderMetadata(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        GlintMetadata.setupWithItem(stack);
    }

    @WrapWithCondition(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private boolean glintcolorizer$disableDepthFunction(int factor) {
        // this is the normal code, which we execute if we aren't rendering a shiny potion
        return GlintMetadata.getRenderMode() != GlintMetadata.RenderMode.SHINY;
    }

//    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
//    private void glintcolorizer$onRenderItemFirstPass(ItemStack stack, IBakedModel model, CallbackInfo ci) {
//        if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
//            GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
//            if (options.disablePotionGlint() || options.useCustomColor()) {
//                renderEffect(model);
//            }
//        }
//    }

//    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER))
//    private void glintcolorizer$onRenderItemSecondPass(ItemStack stack, IBakedModel model, CallbackInfo ci) {
//        if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
//            GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
//            if (options.useCustomColor() && !options.disablePotionGlint()) {
//                SecondGlintHandler.renderEffect((RenderItem) (Object) this, model, textureManager, getResItemGlint());
//            }
//        }
//    }

//    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"))
//    private boolean glintcolorizer$disableEnchantedEffect(ItemStack instance, Operation<Boolean> original) {
//        if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
//            GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
//            return !options.disablePotionGlint() && !options.useCustomColor();
//        } else {
//            return original.call(instance);
//        }
//    }

//    @Inject(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
//    private void glintcolorizer$enableFullSlotSize(IBakedModel model, CallbackInfo ci) {
//        if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
//            GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
//            if (options.useFullSlotShine()) {
//                GlStateManager.scale(1.25, 1.25, 1.25);
//                GlStateManager.translate(-0.1, -0.1, 0.0);
//            }
//        }
//    }
}
