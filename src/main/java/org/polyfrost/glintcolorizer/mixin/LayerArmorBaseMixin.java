package org.polyfrost.glintcolorizer.mixin;

import cc.polyfrost.oneconfig.utils.color.ColorUtils;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(
        value = LayerArmorBase.class,
        priority = Integer.MIN_VALUE
)
public class LayerArmorBaseMixin {

    @ModifyArgs(
            method = "renderGlint",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"
            )
    )
    private void glintColorizer$modifyArmorColor(Args args) {
        int color = GlintConfig.INSTANCE.getArmorColor().getRGB();
        args.set(0, (float) ColorUtils.getRed(color) / 255);
        args.set(1, (float) ColorUtils.getGreen(color) / 255);
        args.set(2, (float) ColorUtils.getBlue(color) / 255);
    }

}
