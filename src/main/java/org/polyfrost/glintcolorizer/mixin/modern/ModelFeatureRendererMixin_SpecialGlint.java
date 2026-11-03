package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.9 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if <1.21.11 {
/*import net.minecraft.client.renderer.RenderType;
*///?} else {
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?}
//? if <26.2 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
*///?} else {
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import org.polyfrost.glintcolorizer.render.GlintLayer;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.polyfrost.glintcolorizer.render.GlintVertexConsumer;
import org.spongepowered.asm.mixin.Unique;
//?}

@Mixin(ModelFeatureRenderer.class)
//? if <26.2 {
/*public abstract class ModelFeatureRendererMixin_SpecialGlint {

    //? if <1.21.11 {
    /^@WrapOperation(method = {"renderBatch", "renderTranslucents"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer;renderModel(Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;Lnet/minecraft/client/renderer/RenderType;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/renderer/OutlineBufferSource;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    ^///?} else
    @WrapOperation(method = {"renderBatch", "renderTranslucents"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer;renderModel(Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;Lnet/minecraft/client/renderer/rendertype/RenderType;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/renderer/OutlineBufferSource;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V"))
    private void glintColorizer$specialGlint(ModelFeatureRenderer instance, SubmitNodeStorage.ModelSubmit<?> submit, RenderType renderType, VertexConsumer buffer, OutlineBufferSource outlineBuffers, MultiBufferSource.BufferSource crumblingBuffers, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) MultiBufferSource.BufferSource buffers) {
        //? if <1.21.11 {
        /^boolean glint = renderType == RenderType.entityGlint();
        ^///?} else
        boolean glint = renderType == RenderTypes.entityGlint();
        if (glint && GlintContext.isCaptured(submit.pose())) {
            buffer = GlintContext.withCaptured(submit.pose(), () -> buffers.getBuffer(renderType));
        }
        original.call(instance, submit, renderType, buffer, outlineBuffers, crumblingBuffers);
    }
}
*///?} else {
public abstract class ModelFeatureRendererMixin_SpecialGlint extends RenderTypeFeatureRenderer<ModelFeatureRenderer.Submit<?>> {
    @Unique private GlintLayer[] glintColorizer$layers;

    @WrapOperation(method = "prepareModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer;getVertexBuilder(Lnet/minecraft/client/renderer/rendertype/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer glintColorizer$skipVanillaGlint(ModelFeatureRenderer instance, RenderType renderType, Operation<VertexConsumer> original, @Local(argsOnly = true) ModelFeatureRenderer.Submit<?> submit) {
        //? if =26.2 {
        /*boolean glint = renderType == RenderTypes.entityGlint();
        *///?} else
        boolean glint = renderType == RenderTypes.patternedShieldGlint() || renderType == GlintRenderTypes.ITEM_FIRST;
        glintColorizer$layers = glint ? GlintContext.withCaptured(submit.pose(), () -> GlintContext.getReplacesItemGlint()
                ? new GlintLayer[]{GlintContext.itemLayer(false, true), GlintContext.itemLayer(true, true)}
                : null) : null;
        return glintColorizer$layers != null ? GlintVertexConsumer.NONE : original.call(instance, renderType);
    }

    @WrapOperation(method = "prepareModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void glintColorizer$drawGlint(Model<?> model, PoseStack poseStack, VertexConsumer buffer, int light, int overlay, int color, Operation<Void> original, @Local(argsOnly = true) ModelFeatureRenderer.Submit<?> submit) {
        GlintLayer[] layers = glintColorizer$layers;
        if (layers == null) {
            original.call(model, poseStack, buffer, light, overlay, color);
            return;
        }
        glintColorizer$layers = null;
        original.call(model, poseStack, glintColorizer$layerBuffer(submit, GlintRenderTypes.ITEM_FIRST, layers[0]), light, overlay, color);
        original.call(model, poseStack, glintColorizer$layerBuffer(submit, GlintRenderTypes.ITEM_SECOND, layers[1]), light, overlay, color);
    }

    @Unique
    private VertexConsumer glintColorizer$layerBuffer(ModelFeatureRenderer.Submit<?> submit, RenderType renderType, GlintLayer layer) {
        VertexConsumer buffer = new GlintVertexConsumer(getVertexBuilder(renderType), layer);
        //? if =26.2 {
        /*return submit.sprite() != null ? submit.sprite().wrap(buffer) : buffer;
        *///?} else
        return submit.uvMapping() != null ? submit.uvMapping().wrap(buffer) : buffer;
    }
}
//?}
//?}
