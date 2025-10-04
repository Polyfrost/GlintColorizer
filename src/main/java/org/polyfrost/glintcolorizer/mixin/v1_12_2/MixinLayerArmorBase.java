package org.polyfrost.glintcolorizer.mixin.v1_12_2;

import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;

//#if MC <= 1.12.2
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.deftu.omnicore.api.color.OmniColor;
import org.polyfrost.glintcolorizer.GlintLayer;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#endif

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase {
    //#if MC <= 1.12.2
    @Inject(
            //#if MC < 1.12.2
            method = "renderGlint",
            //#else
            //$$method = "renderEnchantedGlint",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private
    //#if MC == 1.12.2
    //$$static
    //#endif
    void glintcolorizer$disableArmorGlint(CallbackInfo ci) {
        if (GlintConfig.INSTANCE.enabled && GlintConfig.INSTANCE.getArmorOptions().getEnabled()) {
            ci.cancel();
        }
    }

    @WrapOperation(
            //#if MC < 1.12.2
            method = "renderGlint",
            //#else
            //$$method = "renderEnchantedGlint",
            //#endif
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V")
    )
    private
    //#if MC == 1.12.2
    //$$static
    //#endif
    void glintcolorizer$modifyArmorGlintColor(float red, float green, float blue, float alpha, Operation<Void> original) {
        if (GlintConfig.INSTANCE.enabled) {
            OmniColor color = new OmniColor(GlintMetadata.getGlintColor(GlintLayer.FIRST, true));
            red = color.getRed() / 255F;
            green = color.getGreen() / 255F;
            blue = color.getBlue() / 255F;
            alpha = color.getAlpha() / 255F;
        }

        original.call(red, green, blue, alpha);
    }
    //#endif
}
