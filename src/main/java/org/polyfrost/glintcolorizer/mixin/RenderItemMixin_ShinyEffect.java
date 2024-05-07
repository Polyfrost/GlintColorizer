package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin_ShinyEffect {

    @Shadow protected abstract void renderEffect(IBakedModel model);

    @Shadow @Final private static ResourceLocation RES_ITEM_GLINT;

    @Shadow @Final private TextureManager textureManager;

    @Shadow protected abstract void renderModel(IBakedModel model, int color);

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
            at = @At(
                    "HEAD"
            )
    )
    private void glintColorizer$setItemRendering(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        RenderItemHook.INSTANCE.setItemStack(stack);
    }

    @Redirect(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"
            )
    )
    private void glintColorizer$disableDepthFunc(int factor) {
        if (RenderItemHook.INSTANCE.shouldSkipGlintRendering()) {
            GlStateManager.depthFunc(factor);
        }
    }

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"
            )
    )
    private void glintColorizer$onRenderModel(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.isPotionGlintEnabled()) { return; }
        if (GlintConfig.INSTANCE.getPotionGlintBackground() && RenderItemHook.INSTANCE.isRenderingInGUI() && RenderItemHook.INSTANCE.isPotionItem() && stack.hasEffect()) {
            renderEffect(model);
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
        if (GlintConfig.INSTANCE.getPotionGlintBackground() && RenderItemHook.INSTANCE.isRenderingInGUI() && RenderItemHook.INSTANCE.isPotionItem() && stack.hasEffect()) {
            GlStateManager.pushMatrix();
            GlStateManager.depthMask(false);
            GlStateManager.depthFunc(514);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(768, 1);
            this.textureManager.bindTexture(RES_ITEM_GLINT);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(8.0f, 8.0f, 8.0f);
            float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f;
            GlStateManager.translate(f, 0.0f, 0.0f);
            GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f);
            this.renderModel(model, GlintConfig.INSTANCE.getGUIcolor().getRGB());
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(8.0f, 8.0f, 8.0f);
            float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0f / 8.0f;
            GlStateManager.translate(-f1, 0.0f, 0.0f);
            GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f);
            this.renderModel(model, GlintConfig.INSTANCE.getGUIcolor().getRGB());
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableLighting();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.popMatrix();
        }
    }

    @Redirect(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;hasEffect()Z"
            )
    )
    private boolean glintColorizer$disableRenderEffect(ItemStack instance) {
        if (RenderItemHook.INSTANCE.isPotionGlintEnabled() && RenderItemHook.INSTANCE.isRenderingInGUI() && RenderItemHook.INSTANCE.isPotionItem()) {
            return !GlintConfig.INSTANCE.getPotionGlintBackground();
        }
        return instance.hasEffect();
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
        if (!RenderItemHook.INSTANCE.isPotionGlintEnabled()) { return; }
        if (GlintConfig.INSTANCE.getPotionGlintSize() && RenderItemHook.INSTANCE.isRenderingInGUI() && RenderItemHook.INSTANCE.isPotionItem()) {
            GlStateManager.scale(1.25, 1.25, 1.25);
            GlStateManager.translate(-0.1, -0.1, 0.0);
        }
    }

}
