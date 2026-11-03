package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.9 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.PoseStack;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PoseStack.Pose.class)
public abstract class PoseMixin_CaptureItem {

    @ModifyReturnValue(method = "copy", at = @At("RETURN"))
    private PoseStack.Pose glintColorizer$capture(PoseStack.Pose copy) {
        GlintContext.capture(copy);
        return copy;
    }
}
//?}
