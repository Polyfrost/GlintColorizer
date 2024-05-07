package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Objects;

@Mixin(RenderItem.class)
public class RenderItemMixin_GlintCustomizer {

    @Unique private HashMap<Integer, Integer> glintColorizer$cachedColors = new HashMap<>();

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "HEAD"
            )
    )
    private void glintColorizer$push(IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.isPotionGlintEnabled()) { return; }
        if (GlintConfig.INSTANCE.getPotionGlintSize() && RenderItemHook.INSTANCE.isRenderingInGUI() &&
                RenderItemHook.INSTANCE.isPotionItem() && GlintConfig.INSTANCE.getPotionGlintBackground()) {
            GlStateManager.pushMatrix();
        }
    }

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "TAIL"
            )
    )
    private void glintColorizer$pop(IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.isPotionGlintEnabled()) { return; }
        if (GlintConfig.INSTANCE.getPotionGlintSize() && RenderItemHook.INSTANCE.isRenderingInGUI() &&
                RenderItemHook.INSTANCE.isPotionItem() && GlintConfig.INSTANCE.getPotionGlintBackground()) {
            GlStateManager.popMatrix();
        }
    }

    @ModifyArg(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V",
                    ordinal = 0
            ),
            index = 1
    )
    private int glintColorizer$modifyColor1(int color) {
        if (!GlintConfig.INSTANCE.enabled) { return color; }
        if (RenderItemHook.INSTANCE.isRenderingHeld()) {
            return GlintConfig.INSTANCE.getHeldColor().getRGB();
        }
        if (RenderItemHook.INSTANCE.isRenderingInGUI()) {
            if (GlintConfig.INSTANCE.getPotionBasedColor() && RenderItemHook.INSTANCE.isPotionItem()) {
                return glintColorizer$getPotionColor(Objects.requireNonNull(RenderItemHook.INSTANCE.getItemStack()));
            }
            return GlintConfig.INSTANCE.getGUIcolor().getRGB();
        }
        if (RenderItemHook.INSTANCE.isRenderingDropped()) {
            return GlintConfig.INSTANCE.getDroppedColor().getRGB();
        }
        if (RenderItemHook.INSTANCE.isRenderingFramed()) {
            return GlintConfig.INSTANCE.getFramedColor().getRGB();
        }
        return color;

//        return GlintConfig.individualStrokes ? GlintConfig.strokeOne.getRGB() : GlintConfig.color.getRGB();
    }

    @ModifyArg(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V",
                    ordinal = 1
            ),
            index = 1
    )
    private int glintColorizer$modifyColor2(int color) {
        if (!GlintConfig.INSTANCE.enabled) { return color; }
        if (RenderItemHook.INSTANCE.isRenderingHeld()) {
            return GlintConfig.INSTANCE.getHeldColor().getRGB();
        }
        if (RenderItemHook.INSTANCE.isRenderingInGUI()) {
            if (GlintConfig.INSTANCE.getPotionBasedColor() && RenderItemHook.INSTANCE.isPotionItem()) {
                return glintColorizer$getPotionColor(Objects.requireNonNull(RenderItemHook.INSTANCE.getItemStack()));
            }
            return GlintConfig.INSTANCE.getGUIcolor().getRGB();
        }
        if (RenderItemHook.INSTANCE.isRenderingDropped()) {
            return GlintConfig.INSTANCE.getDroppedColor().getRGB();
        }
        if (RenderItemHook.INSTANCE.isRenderingFramed()) {
            return GlintConfig.INSTANCE.getFramedColor().getRGB();
        }
        return color;
//        return GlintConfig.INSTANCE.getIndividualStrokes() ? GlintConfig.INSTANCE.getStrokeTwo().getRGB() : GlintConfig.INSTANCE.getColor().getRGB();
    }

    /**
     * </><a href="https://github.com/RoccoDev/ShinyPots-1.8">Adapted from ShinyPots by RoccoDev under the LGPL-3.0 license.</a>
     */
    @Unique
    private int glintColorizer$getPotionColor(ItemStack item) {
        int potionId = item.getMetadata();
        Integer cached = glintColorizer$cachedColors.get(potionId);
        if (cached != null) {
            return cached;
        } else {
            int color = Items.potionitem.getColorFromItemStack(item, 0) | 0xFF000000;
            glintColorizer$cachedColors.put(potionId, color);
            return color;
        }
    }

//    @Unique
//    private void glintColorizer$configColors() {
//
//    }

}
