package cc.woverflow.glintcolorizer.mixin;

import cc.polyfrost.oneconfig.utils.color.ColorUtils;
import cc.woverflow.glintcolorizer.config.GlintConfig;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = LayerArmorBase.class, priority = Integer.MIN_VALUE)
public class LayerArmorBaseMixin {
    private static final String RENDER_GLINT =
            //#if MC<=10809
            "renderGlint";
            //#else
            //$$ "renderEnchantedGlint";
            //#endif

    @ModifyArgs(method = RENDER_GLINT, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private
    //#if MC>=11200
    //$$ static
    //#endif
    void modifyColor(Args args) {
        int color = GlintConfig.color.getRGB();
        args.set(0, (float) ColorUtils.getRed(color) / 255);
        args.set(1, (float) ColorUtils.getGreen(color) / 255);
        args.set(2, (float) ColorUtils.getBlue(color) / 255);
    }
}
