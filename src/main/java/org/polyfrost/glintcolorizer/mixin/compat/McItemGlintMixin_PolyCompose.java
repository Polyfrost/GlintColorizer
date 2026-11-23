package org.polyfrost.glintcolorizer.mixin.compat;

import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.config.GlintEffectOptions;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.polyfrost.compose.minecraft.mc.McItemGlint", remap = false)
public class McItemGlintMixin_PolyCompose {

    @Unique private static final int FIRST_STROKE_TINT = 6;
    @Unique private static final int SECOND_STROKE_TINT = 15;

    @Inject(method = "update", at = @At("RETURN"), require = 0)
    private void glintColorizer$color(CallbackInfoReturnable<float[]> cir) {
        if (!GlintConfig.INSTANCE.getEnabled()) { return; }
        GlintEffectOptions settings = GlintConfig.INSTANCE.getGuiItem();
        float[] layers = cir.getReturnValue();
        glintColorizer$tint(layers, FIRST_STROKE_TINT, RenderItemHook.INSTANCE.strokeColor(settings, true));
        glintColorizer$tint(layers, SECOND_STROKE_TINT, RenderItemHook.INSTANCE.strokeColor(settings, false));
    }

    @Unique
    private static void glintColorizer$tint(float[] layers, int offset, int color) {
        layers[offset] = (color >> 16 & 255) / 255.0F;
        layers[offset + 1] = (color >> 8 & 255) / 255.0F;
        layers[offset + 2] = (color & 255) / 255.0F;
    }

}
