package org.polyfrost.glintcolorizer.mixin;

import org.polyfrost.glintcolorizer.config.GlintEffectOptions;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RenderItem.class)
public class RenderItemMixin_GlintCustomizer {

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "HEAD"
            )
    )
    private void glintColorizer$push(IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.getShouldRenderFullSlot()) { return; }
        GlStateManager.pushMatrix();
    }

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "TAIL"
            )
    )
    private void glintColorizer$pop(IBakedModel model, CallbackInfo ci) {
        if (!RenderItemHook.INSTANCE.getShouldRenderFullSlot()) { return; }
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
        if (!GlintConfig.INSTANCE.getEnabled()) { return; }
        args.set(0, glintColorizer$getModifiedScale(args.get(0)));
        args.set(1, glintColorizer$getModifiedScale(args.get(1)));
        args.set(2, glintColorizer$getModifiedScale(args.get(2)));
    }

    @Redirect(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;getSystemTime()J"
            )
    )
    private long glintColorizer$modifySpeed() {
        long time = Minecraft.getSystemTime();
        if (!GlintConfig.INSTANCE.getEnabled()) { return time; }
        GlintEffectOptions settings = RenderItemHook.INSTANCE.getActiveOptions();
        if (settings == null) { return time; }
        return (long) (time * (double) settings.getSpeed());
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
        if (!GlintConfig.INSTANCE.getEnabled()) { return angle; }
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
        if (!GlintConfig.INSTANCE.getEnabled()) { return angle; }
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
        if (!GlintConfig.INSTANCE.getEnabled()) { return color; }
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
        if (!GlintConfig.INSTANCE.getEnabled()) { return color; }
        return glintColorizer$getModifiedColor(color, false);
    }

    @Unique
    private int glintColorizer$getModifiedColor(int color, boolean isFirstStroke) {
        GlintEffectOptions settings = RenderItemHook.INSTANCE.getActiveOptions();
        if (settings == null) { return color; }
        return RenderItemHook.INSTANCE.glintColor(settings, isFirstStroke);
    }

    @Unique
    private float glintColorizer$getModifiedRotation(float defaultRot, boolean isFirstStroke) {
        GlintEffectOptions settings = RenderItemHook.INSTANCE.getActiveOptions();
        if (settings == null) { return defaultRot; }
        return isFirstStroke ? settings.getStrokeRotOne() : settings.getStrokeRotTwo();
    }

    @Unique
    private float glintColorizer$getModifiedScale(float originalScale) {
        GlintEffectOptions settings = RenderItemHook.INSTANCE.getActiveOptions();
        if (settings == null) { return originalScale; }
        return settings.getScale() * originalScale;
    }

}
