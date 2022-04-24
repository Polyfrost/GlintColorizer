package cc.woverflow.glintcolorizer.mixin;

import cc.woverflow.glintcolorizer.config.GlintConfig;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LayerArmorBase.class)
public class LayerArmorBaseMixin {
    @ModifyArgs(method = "renderGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private void modifyColor(Args args) {
        args.set(0, (float) GlintConfig.color.getRed() / 255);
        args.set(1, (float) GlintConfig.color.getGreen() / 255);
        args.set(2, (float) GlintConfig.color.getBlue() / 255);
    }
}
