package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.4 {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

//? if <1.21.9 {
//import net.minecraft.client.renderer.MultiBufferSource;
//?} else
import net.minecraft.client.renderer.SubmitNodeCollector;

@Mixin(ItemStackRenderState.class)
public abstract class ItemStackRenderStateMixin_GlintContext {
    @Shadow
    ItemDisplayContext displayContext;

    //? if <1.21.9 {
    /*@WrapMethod(method = "render")
    private void glintColorizer$trackItem(PoseStack poseStack, MultiBufferSource buffers, int light, int overlay, Operation<Void> original) {
        GlintContext.withSpecialItem(displayContext, () -> original.call(poseStack, buffers, light, overlay));
    }
    *///?} else {
    @WrapMethod(method = "submit")
    private void glintColorizer$trackItem(PoseStack poseStack, SubmitNodeCollector collector, int light, int overlay, int outlineColor, Operation<Void> original) {
        GlintContext.withSpecialItem(displayContext, () -> original.call(poseStack, collector, light, overlay, outlineColor));
    }
    //?}
}
//?}
