package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=26.1 {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;

//? if =26.1 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
*///?} else {
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import org.polyfrost.glintcolorizer.render.GlintLayer;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.polyfrost.glintcolorizer.render.GlintVertexConsumer;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
//?}
//? if =26.2
//import org.spongepowered.asm.mixin.Shadow;
//? if >=26.3 {
import net.minecraft.client.renderer.item.ItemStackRenderState;
//?}

@Mixin(ItemFeatureRenderer.class)
//? if =26.1 {
/*public abstract class ItemFeatureRendererMixin_GlintContext {

    @WrapMethod(method = "renderItem")
    private void glintColorizer$trackItem(MultiBufferSource.BufferSource bufferSource, OutlineBufferSource outlineBufferSource, SubmitNodeStorage.ItemSubmit submit, Operation<Void> original) {
        GlintContext.withItem(submit.displayContext(), submit.tintLayers(), () -> original.call(bufferSource, outlineBufferSource, submit));
    }
}
*///?} else {
public abstract class ItemFeatureRendererMixin_GlintContext extends RenderTypeFeatureRenderer<ItemFeatureRenderer.Submit> {

    @Unique private GlintLayer glintColorizer$first;
    @Unique private GlintLayer glintColorizer$second;

    //? if =26.2 {
    //@WrapMethod(method = "prepareFoilSubmit")
    //?} else
    @WrapMethod(method = "prepareMainSubmit")
    private void glintColorizer$trackItem(ItemFeatureRenderer.Submit submit, Operation<Void> original) {
        GlintContext.withItem(submit.displayContext(), submit.tintLayers(), () -> {
            boolean replace = GlintContext.getReplacesItemGlint();
            glintColorizer$first = replace ? GlintContext.itemLayer(false, false) : null;
            glintColorizer$second = replace ? GlintContext.itemLayer(true, false) : null;
            try {
                original.call(submit);
            } finally {
                glintColorizer$first = null;
                glintColorizer$second = null;
            }
        });
    }

    //? if =26.2 {
    /*@Unique private boolean glintColorizer$translucent;

    @Shadow
    private static boolean useTransparentGlint(RenderType renderType) {
        throw new AssertionError();
    }

    @WrapOperation(method = "prepareFoilSubmit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ItemFeatureRenderer;getFoilBuffer(Lnet/minecraft/client/renderer/rendertype/RenderType;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer glintColorizer$skipVanillaGlint(ItemFeatureRenderer instance, RenderType renderType, PoseStack.Pose foilDecalPose, Operation<VertexConsumer> original) {
        if (glintColorizer$first == null) {
            return original.call(instance, renderType, foilDecalPose);
        }
        glintColorizer$translucent = useTransparentGlint(renderType);
        return GlintVertexConsumer.NONE;
    }

    @WrapOperation(method = "prepareFoilSubmit", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBakedQuad(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/resources/model/geometry/BakedQuad;Lcom/mojang/blaze3d/vertex/QuadInstance;)V"))
    private void glintColorizer$drawGlint(VertexConsumer buffer, PoseStack.Pose pose, BakedQuad quad, QuadInstance instance, Operation<Void> original) {
        if (glintColorizer$first == null) {
            original.call(buffer, pose, quad, instance);
        } else {
            glintColorizer$putGlint(pose, quad, instance);
        }
    }
    *///?} else {
    @WrapOperation(method = "prepareMainSubmit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ItemFeatureRenderer$Submit;foilType()Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;"))
    private ItemStackRenderState.FoilType glintColorizer$skipShaderGlint(ItemFeatureRenderer.Submit submit, Operation<ItemStackRenderState.FoilType> original) {
        ItemStackRenderState.FoilType foilType = original.call(submit);
        if (glintColorizer$first == null || foilType == ItemStackRenderState.FoilType.NONE) {
            glintColorizer$first = null;
            glintColorizer$second = null;
            return foilType;
        }
        return ItemStackRenderState.FoilType.NONE;
    }

    @WrapOperation(method = "prepareMainSubmit", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBakedQuad(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/resources/model/geometry/BakedQuad;Lcom/mojang/blaze3d/vertex/QuadInstance;)V"))
    private void glintColorizer$drawGlint(VertexConsumer buffer, PoseStack.Pose pose, BakedQuad quad, QuadInstance instance, Operation<Void> original) {
        original.call(buffer, pose, quad, instance);
        if (glintColorizer$first != null) {
            glintColorizer$putGlint(pose, quad, instance);
        }
    }
    //?}

    @Unique
    private void glintColorizer$putGlint(PoseStack.Pose pose, BakedQuad quad, QuadInstance instance) {
        RenderType first = GlintRenderTypes.ITEM_FIRST;
        RenderType second = GlintRenderTypes.ITEM_SECOND;
        //? if =26.2 {
        /*if (glintColorizer$translucent) {
            first = GlintRenderTypes.ITEM_TRANSLUCENT_FIRST;
            second = GlintRenderTypes.ITEM_TRANSLUCENT_SECOND;
        }
        *///?}
        new GlintVertexConsumer(getVertexBuilder(first), glintColorizer$first).putBakedQuad(pose, quad, instance);
        new GlintVertexConsumer(getVertexBuilder(second), glintColorizer$second).putBakedQuad(pose, quad, instance);
    }
}
//?}
//?}
