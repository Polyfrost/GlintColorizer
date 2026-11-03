package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=26.3 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.client.renderer.texture.UvMapping;
import net.minecraft.resources.Identifier;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShieldSpecialRenderer.class)
public abstract class ShieldSpecialRendererMixin_SpecialGlint {
    @Unique private boolean glintColorizer$pendingGlint;

    @WrapOperation(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;entitySolidGlint(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType glintColorizer$plainModel(Identifier texture, Operation<RenderType> original) {
        if (!GlintContext.getReplacesItemGlint()) {
            return original.call(texture);
        }
        glintColorizer$pendingGlint = true;
        return RenderTypes.entitySolid(texture);
    }

    @WrapOperation(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/UvMapping;I)V"))
    private <S> void glintColorizer$submitGlint(SubmitNodeCollector collector, Model<? super S> model, S state, PoseStack poseStack, RenderType renderType, int light, int overlay, int color, UvMapping uvMapping, int outlineColor, Operation<Void> original) {
        original.call(collector, model, state, poseStack, renderType, light, overlay, color, uvMapping, outlineColor);
        if (glintColorizer$pendingGlint) {
            glintColorizer$pendingGlint = false;
            original.call(collector, model, state, poseStack, GlintRenderTypes.ITEM_FIRST, light, overlay, -1, uvMapping, 0);
        }
    }
}
//?}
