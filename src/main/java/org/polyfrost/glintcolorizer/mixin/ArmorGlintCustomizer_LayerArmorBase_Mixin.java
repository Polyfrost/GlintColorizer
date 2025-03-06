package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LayerArmorBase.class, priority = Integer.MIN_VALUE)
public abstract class ArmorGlintCustomizer_LayerArmorBase_Mixin implements LayerRenderer<EntityLivingBase> {

    @Inject(method = "renderGlint", at = @At("HEAD"), cancellable = true)
    private void disableArmorGlint(CallbackInfo ci) {
        if (GlintConfig.INSTANCE.enabled && GlintConfig.INSTANCE.isArmorGlintDisabled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "renderGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private void modifyArmorGlintColor(float r, float g, float b, float a) {
        if (!GlintConfig.INSTANCE.enabled) {
            GlStateManager.color(r, g, b, a);
        } else {
            PolyColor color = GlintConfig.INSTANCE.getArmorColor();
            GlStateManager.color(color.red() / 255f, color.green() / 255f, color.blue() / 255f, color.alpha() / 255f);
        }
    }

}
