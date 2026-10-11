package org.polyfrost.glintcolorizer.mixin.compat;

import net.minecraft.client.renderer.GlStateManager;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.config.GlintEffectOptions;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev.rdh.argentum.impl.render.gui.hud.item.GuiItemGlints", remap = false, priority = 1100)
public class GuiItemGlintsMixin_Argentum {

    @Unique private boolean glintColorizer$styling;

    @Inject(method = "flush", at = @At("HEAD"), require = 0)
    private void glintColorizer$beginBatch(int atlasTexture, CallbackInfo ci) {
        RenderItemHook.INSTANCE.beginGuiBatch();
        glintColorizer$styling = false;
    }

    @Inject(method = "drawPass", at = @At("HEAD"), require = 0)
    private void glintColorizer$color(int period, float direction, float rotation, CallbackInfo ci) {
        glintColorizer$styling = GlintConfig.INSTANCE.getEnabled();
        if (!glintColorizer$styling) { return; }
        int color = RenderItemHook.INSTANCE.strokeColor(GlintConfig.INSTANCE.getGuiItem(), direction > 0.0F);
        GlStateManager.color((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, 1.0F);
    }

    @ModifyVariable(method = "drawPass", at = @At("HEAD"), argsOnly = true, ordinal = 0, require = 0)
    private int glintColorizer$speed(int period) {
        if (!GlintConfig.INSTANCE.getEnabled()) { return period; }
        GlintEffectOptions settings = GlintConfig.INSTANCE.getGuiItem();
        float rate = settings.getSpeed() * settings.getScale();
        return rate > 0.0F ? Math.max(1, (int) (period / rate)) : Integer.MAX_VALUE;
    }

    @ModifyVariable(method = "drawPass", at = @At("HEAD"), argsOnly = true, ordinal = 1, require = 0)
    private float glintColorizer$rotation(float rotation, int period, float direction) {
        if (!GlintConfig.INSTANCE.getEnabled()) { return rotation; }
        GlintEffectOptions settings = GlintConfig.INSTANCE.getGuiItem();
        return direction > 0.0F ? settings.getStrokeRotOne() : settings.getStrokeRotTwo();
    }

    @ModifyVariable(method = "vertex", at = @At("HEAD"), argsOnly = true, ordinal = 5, require = 0)
    private float glintColorizer$scaleU(float glintU) {
        return glintU * glintColorizer$scale();
    }

    @ModifyVariable(method = "vertex", at = @At("HEAD"), argsOnly = true, ordinal = 6, require = 0)
    private float glintColorizer$scaleV(float glintV) {
        return glintV * glintColorizer$scale();
    }

    @Unique
    private float glintColorizer$scale() {
        return glintColorizer$styling ? GlintConfig.INSTANCE.getGuiItem().getScale() : 1.0F;
    }

}
