package org.polyfrost.glintcolorizer.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.handler.SecondGlintHandler;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin_ShinyEffect {

    @Shadow protected abstract void renderEffect(IBakedModel model);
    @Shadow @Final private static ResourceLocation RES_ITEM_GLINT;
    @Shadow @Final private TextureManager textureManager;

    @Unique private boolean glintColorizer$behindItem;

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
            at = @At(
                    "HEAD"
            )
    )
    private void glintColorizer$setItemRendering(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        RenderItemHook.INSTANCE.setItemStack(stack);
    }

    @WrapWithCondition(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"
            )
    )
    private boolean glintColorizer$disableDepthFunc(int factor) {
        return RenderItemHook.INSTANCE.shouldSkipGlintRendering();
    }

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"
            )
    )
    private void glintColorizer$onRenderModel(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (RenderItemHook.INSTANCE.isShinyEffectActive() && glintColorizer$isValidItem(stack)) {
            glintColorizer$behindItem = true;
            try {
                renderEffect(model);
            } finally {
                glintColorizer$behindItem = false;
            }
        }
    }

    @WrapOperation(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;blendFunc(II)V",
                    ordinal = 0
            )
    )
    private void glintColorizer$opaqueShinyEffect(int source, int destination, Operation<Void> original) {
        if (glintColorizer$behindItem && GlintConfig.INSTANCE.getPotionGlintOpaque()) {
            original.call(1, 0);
        } else {
            original.call(source, destination);
        }
    }

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V",
                    ordinal = 1
            )
    )
    private void glintColorizer$additiveSecondStroke(IBakedModel model, CallbackInfo ci) {
        if (glintColorizer$behindItem && GlintConfig.INSTANCE.getPotionGlintOpaque()) {
            GlStateManager.blendFunc(768, 1);
        }
    }

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void glintColorizer$onRenderModel2(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.isPotionGlintEnabled()) { return; }
        if (GlintConfig.INSTANCE.getPotionGlintBackground() && !GlintConfig.INSTANCE.getPotionGlintForeground() && glintColorizer$isValidItem(stack)) {
            RenderItem instance = (RenderItem) (Object) this;
            SecondGlintHandler.renderEffect(instance, model, textureManager, RES_ITEM_GLINT);
        }
    }

    @WrapOperation(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"
            )
    )
    private boolean glintColorizer$disableRenderEffect(ItemStack instance, Operation<Boolean> original) {
        if (RenderItemHook.INSTANCE.isShinyEffectActive() && glintColorizer$isValidItem(instance)) { return false; }
        return instance.getItem() != null && original.call(instance);
    }

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
                    ordinal = 0
            )
    )
    private void glintColorizer$fullSlotSize(IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.getShouldRenderFullSlot()) { return; }
        GlStateManager.scale(1.25, 1.25, 1.25);
        GlStateManager.translate(-0.1, -0.1, 0.0);
    }

    @Unique
    private boolean glintColorizer$isValidItem(ItemStack stack) {
        return RenderItemHook.INSTANCE.isRenderingInGUI() && RenderItemHook.INSTANCE.isPotionItem();
    }

}
