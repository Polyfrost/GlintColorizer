package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.polyfrost.polyui.color.ColorUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(
        value = LayerArmorBase.class,
        priority = Integer.MIN_VALUE
)
public abstract class LayerArmorBaseMixin<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {

    @Inject(
            method = "renderGlint",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void glintColorizer$disableGlint(EntityLivingBase entitylivingbaseIn, T modelbaseIn, float p_177183_3_, float p_177183_4_, float partialTicks, float p_177183_6_, float p_177183_7_, float p_177183_8_, float scale, CallbackInfo ci) {
        if (GlintConfig.INSTANCE.getArmorGlintToggle() && GlintConfig.INSTANCE.enabled) {
            ci.cancel();
        }
    }

    @ModifyArgs(
            method = "renderGlint",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"
            )
    )
    private void glintColorizer$modifyArmorColor(Args args) {
        int color = GlintConfig.INSTANCE.getArmorColor().getRgba();
        args.set(0, (float) ColorUtils.getRed(color) / 255);
        args.set(1, (float) ColorUtils.getGreen(color) / 255);
        args.set(2, (float) ColorUtils.getBlue(color) / 255);
    }

}
