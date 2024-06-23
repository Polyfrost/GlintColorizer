package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.polyfrost.glintcolorizer.config.GlintEffectOptions;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.HashMap;

@Mixin(RenderItem.class)
public class RenderItemMixin_GlintCustomizer {

    @Unique private final HashMap<Integer, Integer> glintColorizer$cachedColors = new HashMap<>();

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "HEAD"
            )
    )
    private void glintColorizer$push(IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.isPotionGlintEnabled()) { return; }
        if (!glintColorizer$shouldApplyMatrix()) { return; }
        GlStateManager.pushMatrix();
    }

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "TAIL"
            )
    )
    private void glintColorizer$pop(IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.isPotionGlintEnabled()) { return; }
        if (glintColorizer$shouldApplyMatrix()) { return; }
        GlStateManager.popMatrix();
    }

    @ModifyArgs(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"
            )
    )
    private void glintColorizer$modifyScale(Args args) {
        if (!GlintConfig.INSTANCE.enabled) { return; }
        args.set(0, glintColorizer$getModifiedScale(args.get(0)));
        args.set(1, glintColorizer$getModifiedScale(args.get(1)));
        args.set(2, glintColorizer$getModifiedScale(args.get(2)));
    }

    @ModifyArg(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"
            ),
            index = 0
    )
    private float glintColorizer$modifySpeed(float speed) {
        if (!GlintConfig.INSTANCE.enabled) { return speed; }
        return glintColorizer$getModifiedSpeed(speed);
    }

    @ModifyArg(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V",
                    ordinal = 0
            ),
            index = 0
    )
    private float glintColorizer$modifyRotation(float angle) {
        if (!GlintConfig.INSTANCE.enabled) { return angle; }
        return glintColorizer$getModifiedRotation(angle , true);
    }

    @ModifyArg(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V",
                    ordinal = 1
            ),
            index = 0
    )
    private float glintColorizer$modifyRotation2(float angle) {
        if (!GlintConfig.INSTANCE.enabled) { return angle; }
        return glintColorizer$getModifiedRotation(angle, false);
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
        return glintColorizer$getModifiedColor(color, true);
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
        return glintColorizer$getModifiedColor(color, false);
    }

    @Unique
    private int glintColorizer$getModifiedColor(int color, boolean isFirstStroke) {
        if (RenderItemHook.INSTANCE.isRenderingHeld()) {
            return glintColorizer$getColor(GlintConfig.INSTANCE.getHeldItem(), isFirstStroke);
        }

        if (RenderItemHook.INSTANCE.isRenderingInGUI()) {
            if (GlintConfig.INSTANCE.getPotionBasedColor() && RenderItemHook.INSTANCE.isPotionItem()) {
                return glintColorizer$getPotionColor(RenderItemHook.INSTANCE.getItemStack());
            }
            if (GlintConfig.INSTANCE.getPotionGlintBackground() && RenderItemHook.INSTANCE.isPotionItem()) {
                return glintColorizer$getColor(GlintConfig.INSTANCE.getShinyPots(), isFirstStroke);
            }
            return glintColorizer$getColor(GlintConfig.INSTANCE.getGuiItem(), isFirstStroke);
        }

        if (RenderItemHook.INSTANCE.isRenderingDropped()) {
            return glintColorizer$getColor(GlintConfig.INSTANCE.getDroppedItem(), isFirstStroke);
        }

        if (RenderItemHook.INSTANCE.isRenderingFramed()) {
            return glintColorizer$getColor(GlintConfig.INSTANCE.getFramedItem(), isFirstStroke);
        }

        return color;
    }

    @Unique
    private int glintColorizer$getColor(GlintEffectOptions settings, boolean isFirstStroke) {
        return settings.getIndividualStrokes() ?
                (isFirstStroke ? settings.getStrokeOneColor().getRGB() : settings.getStrokeTwoColor().getRGB()) :
                settings.getGlintColor().getRGB();
    }

    @Unique
    private float glintColorizer$getModifiedRotation(float defaultRot, boolean isFirstStroke) {
        if (RenderItemHook.INSTANCE.isRenderingHeld()) {
            return isFirstStroke ? GlintConfig.INSTANCE.getHeldItem().getStrokeRotOne() : GlintConfig.INSTANCE.getHeldItem().getStrokeRotTwo();
        }

        if (RenderItemHook.INSTANCE.isRenderingInGUI()) {
            if (GlintConfig.INSTANCE.getPotionBasedColor() && RenderItemHook.INSTANCE.isPotionItem()) {
                return isFirstStroke ? GlintConfig.INSTANCE.getGuiItem().getStrokeRotOne() : GlintConfig.INSTANCE.getGuiItem().getStrokeRotTwo();
            }
            if (GlintConfig.INSTANCE.getPotionGlintBackground() && RenderItemHook.INSTANCE.isPotionItem()) {
                return isFirstStroke ? GlintConfig.INSTANCE.getShinyPots().getStrokeRotOne() : GlintConfig.INSTANCE.getShinyPots().getStrokeRotTwo();
            }
            return isFirstStroke ? GlintConfig.INSTANCE.getGuiItem().getStrokeRotOne() : GlintConfig.INSTANCE.getGuiItem().getStrokeRotTwo();
        }

        if (RenderItemHook.INSTANCE.isRenderingDropped()) {
            return isFirstStroke ? GlintConfig.INSTANCE.getDroppedItem().getStrokeRotOne() : GlintConfig.INSTANCE.getDroppedItem().getStrokeRotTwo();
        }

        if (RenderItemHook.INSTANCE.isRenderingFramed()) {
            return isFirstStroke ? GlintConfig.INSTANCE.getFramedItem().getStrokeRotOne() : GlintConfig.INSTANCE.getFramedItem().getStrokeRotTwo();
        }

        return defaultRot;
    }

    @Unique
    private float glintColorizer$getModifiedSpeed(float defaultSpeed) {
        if (RenderItemHook.INSTANCE.isRenderingHeld()) {
            return GlintConfig.INSTANCE.getHeldItem().getSpeed() * defaultSpeed;
        }

        if (RenderItemHook.INSTANCE.isRenderingInGUI()) {
            if (GlintConfig.INSTANCE.getPotionBasedColor() && RenderItemHook.INSTANCE.isPotionItem()) {
                return GlintConfig.INSTANCE.getGuiItem().getSpeed() * defaultSpeed;
            }
            if (GlintConfig.INSTANCE.getPotionGlintBackground() && RenderItemHook.INSTANCE.isPotionItem()) {
                return GlintConfig.INSTANCE.getShinyPots().getScale() * defaultSpeed;
            }
            return GlintConfig.INSTANCE.getGuiItem().getSpeed() * defaultSpeed;
        }

        if (RenderItemHook.INSTANCE.isRenderingDropped()) {
            return GlintConfig.INSTANCE.getDroppedItem().getSpeed() * defaultSpeed;
        }

        if (RenderItemHook.INSTANCE.isRenderingFramed()) {
            return GlintConfig.INSTANCE.getFramedItem().getSpeed() * defaultSpeed;
        }

        return defaultSpeed;
    }

    @Unique
    private float glintColorizer$getModifiedScale(float originalScale) {
        if (RenderItemHook.INSTANCE.isRenderingHeld()) {
            return GlintConfig.INSTANCE.getHeldItem().getScale() * originalScale;
        }

        if (RenderItemHook.INSTANCE.isRenderingInGUI()) {
            if (GlintConfig.INSTANCE.getPotionBasedColor() && RenderItemHook.INSTANCE.isPotionItem()) {
                return GlintConfig.INSTANCE.getGuiItem().getScale() * originalScale;
            }
            if (GlintConfig.INSTANCE.getPotionGlintBackground() && RenderItemHook.INSTANCE.isPotionItem()) {
                return GlintConfig.INSTANCE.getShinyPots().getScale() * originalScale;
            }
            return GlintConfig.INSTANCE.getGuiItem().getScale() * originalScale;
        }

        if (RenderItemHook.INSTANCE.isRenderingDropped()) {
            return GlintConfig.INSTANCE.getDroppedItem().getScale() * originalScale;
        }

        if (RenderItemHook.INSTANCE.isRenderingFramed()) {
            return GlintConfig.INSTANCE.getFramedItem().getScale() * originalScale;
        }

        return originalScale;
    }

    @Unique
    private boolean glintColorizer$shouldApplyMatrix() {
        return GlintConfig.INSTANCE.getPotionGlintSize() &&
                RenderItemHook.INSTANCE.isRenderingInGUI() &&
                RenderItemHook.INSTANCE.isPotionItem() &&
                (GlintConfig.INSTANCE.getPotionGlintForeground() || GlintConfig.INSTANCE.getPotionGlintBackground());
    }

    /**
     * <a href="https://github.com/RoccoDev/ShinyPots-1.8">Adapted from ShinyPots by RoccoDev under the LGPL-3.0 license.</a>
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

}
