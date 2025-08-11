package org.polyfrost.glintcolorizer.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.polyfrost.polyui.color.PolyColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase implements LayerRenderer<EntityLivingBase> {
    //#if MC < 1.13
    @Inject(method =
            //#if MC < 1.12.2
            "renderGlint",
            //#else
            //$$ "method_12479",
            //#endif
            at = @At("HEAD"), cancellable = true)
    private
    //#if MC >= 1.12.2
    //$$ static
    //#endif
    void glintcolorizer$disableArmorGlint(CallbackInfo ci) {
        if (GlintConfig.INSTANCE.enabled && GlintConfig.INSTANCE.isArmorGlintDisabled()) {
            ci.cancel();
        }
    }

    @WrapOperation(method =
            //#if MC < 1.12.2
            "renderGlint",
            //#else
            //$$ "method_12479",
            //#endif
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private
        //#if MC >= 1.12.2
        //$$ static
        //#endif
    void glintcolorizer$modifyArmorGlintColor(float red, float green, float blue, float alpha, Operation<Void> original) {
        if (GlintConfig.INSTANCE.enabled) {
            PolyColor color = GlintConfig.INSTANCE.getArmorColor();
            red = color.red() / 255F;
            green = color.green() / 255F;
            blue = color.blue() / 255F;
            alpha = color.alpha() / 255F;
        }

        original.call(red, green, blue, alpha);
    }
    //#endif
}
