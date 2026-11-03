package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.4 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.List;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "org.polyfrost.overflowanimations.handler.rendering.pipeline.ArmorGlint", remap = false)
public abstract class ArmorGlintMixin_OverflowAnimations {

    @ModifyReturnValue(method = "layers", at = @At("RETURN"), require = 0)
    private static List<?> glintColorizer$useOwnArmorGlint(List<?> original) {
        return GlintContext.getReplacesArmorGlint() ? List.of() : original;
    }
}
//?}
