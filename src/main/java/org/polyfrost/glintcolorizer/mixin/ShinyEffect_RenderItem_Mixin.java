package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.polyfrost.glintcolorizer.SecondGlintHandler;
import org.polyfrost.glintcolorizer.config.GlintOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class ShinyEffect_RenderItem_Mixin {
    @Shadow
    protected abstract void renderEffect(IBakedModel model);

    @Shadow
    @Final
    private static ResourceLocation RES_ITEM_GLINT;
    @Shadow
    @Final
    private TextureManager textureManager;

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At("HEAD"))
    private void setupRenderMetadata(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        GlintMetadata.setupWithItem(stack);
    }

    @Redirect(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private void disableDepthFunction(int factor) {
        if (GlintMetadata.getRenderMode() != GlintMetadata.RenderMode.SHINY) {
            // this is the normal code, which we execute if we aren't rendering a shiny potion
            GlStateManager.depthFunc(factor);
        }
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void onRenderItemFirstPass(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (GlintMetadata.getRenderMode() != GlintMetadata.RenderMode.SHINY) {
            return;
        }
        GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
        if (options.disablePotionGlint() || options.useCustomColor()) {
            renderEffect(model);
        }
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER))
    private void onRenderItemSecondPass(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (GlintMetadata.getRenderMode() != GlintMetadata.RenderMode.SHINY) {
            return;
        }
        GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
        if (options.useCustomColor() && !options.disablePotionGlint()) {
            RenderItem instance = (RenderItem) (Object) this;
            SecondGlintHandler.renderEffect(instance, model, textureManager, RES_ITEM_GLINT);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"))
    private boolean disableEnchantedEffect(ItemStack instance) {
        if (GlintMetadata.getRenderMode() == GlintMetadata.RenderMode.SHINY) {
            GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
            return !options.disablePotionGlint() && !options.useCustomColor();
        }
        return instance.hasEffect();
    }

    @Inject(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
    private void enableFullSlotSize(IBakedModel model, CallbackInfo ci) {
        if (GlintMetadata.getRenderMode() != GlintMetadata.RenderMode.SHINY) {
            return;
        }
        GlintOptions.ShinyPots options = (GlintOptions.ShinyPots) GlintMetadata.getRenderingOptions();
        if (options.useFullSlotShine()) {
            GlStateManager.scale(1.25, 1.25, 1.25);
            GlStateManager.translate(-0.1, -0.1, 0.0);
        }
    }
}
