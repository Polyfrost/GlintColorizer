package cc.woverflow.glintcolorizer.mixin;

import cc.woverflow.glintcolorizer.config.GlintConfig;
import cc.woverflow.onecore.utils.ColorUtils;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LayerArmorBase.class)
public class LayerArmorBaseMixin {
    @ModifyArgs(method = "renderGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private void modifyColor(Args args) {
        int color = GlintConfig.chroma ? ColorUtils.timeBasedChroma() : GlintConfig.color.getRGB();
        args.set(0, (float) ColorUtils.getRed(color) / 255);
        args.set(1, (float) ColorUtils.getGreen(color) / 255);
        args.set(2, (float) ColorUtils.getBlue(color) / 255);
    }
}
