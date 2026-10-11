package org.polyfrost.glintcolorizer.mixin.compat;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.polyfrost.overflowanimations.legacy.tint.LegacyGlint", remap = false)
public class LegacyGlintMixin_OverflowAnimations {

    @ModifyReturnValue(method = "itemColor", at = @At("RETURN"), require = 0)
    private static int glintColorizer$itemColor(int original, int fallback, int layer) {
        return RenderItemHook.INSTANCE.colorOr(original, layer == 0);
    }

    @ModifyReturnValue(method = "armorColor", at = @At("RETURN"), require = 0)
    private static int glintColorizer$armorColor(int original) {
        return RenderItemHook.INSTANCE.armorColorOr(original);
    }

    @ModifyReturnValue(method = "replacesArmorGlint", at = @At("RETURN"), require = 0)
    private static boolean glintColorizer$disabledArmorGlint(boolean original) {
        return original && !(GlintConfig.INSTANCE.getEnabled() && GlintConfig.INSTANCE.getArmorGlintToggle());
    }

    @Inject(method = "renderGuiGlint", at = @At("HEAD"), cancellable = true, require = 0)
    private static void glintColorizer$shinyPotsOwnPotions(int x, int y, float z, CallbackInfo ci) {
        if (RenderItemHook.INSTANCE.isShinyEffectActive() && RenderItemHook.INSTANCE.isRenderingInGUI() && RenderItemHook.INSTANCE.isPotionItem()) {
            ci.cancel();
        }
    }

}
