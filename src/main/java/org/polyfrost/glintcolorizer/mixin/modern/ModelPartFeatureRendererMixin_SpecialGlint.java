package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.9 && <26.2 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if <1.21.11 {
/^import net.minecraft.client.renderer.RenderType;
^///?} else
import net.minecraft.client.renderer.rendertype.RenderType;

@Mixin(ModelPartFeatureRenderer.class)
public abstract class ModelPartFeatureRendererMixin_SpecialGlint {

    //? if <1.21.11 {
    /^@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;getFoilBuffer(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    ^///?} else if <26.1 {
    /^@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;getFoilBuffer(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/rendertype/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    ^///?} else
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ItemFeatureRenderer;getFoilBuffer(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/rendertype/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer glintColorizer$specialGlint(MultiBufferSource buffers, RenderType renderType, boolean sheeted, boolean foil, Operation<VertexConsumer> original, @Local SubmitNodeStorage.ModelPartSubmit submit) {
        return GlintContext.withCaptured(submit.pose(), () -> original.call(buffers, renderType, sheeted, foil));
    }
}
*///?}
