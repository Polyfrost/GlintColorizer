package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=26.2 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.polyfrost.glintcolorizer.render.GlintVertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RenderTypeFeatureRenderer.class)
public abstract class RenderTypeFeatureRendererMixin_ArmorGlint {

    @ModifyReturnValue(method = "getVertexBuilder", at = @At("RETURN"))
    private VertexConsumer glintColorizer$armorGlint(VertexConsumer buffer, RenderType renderType) {
        if (renderType == GlintRenderTypes.ARMOR_FIRST) {
            return new GlintVertexConsumer(buffer, GlintContext.armorLayer(false));
        }
        if (renderType == GlintRenderTypes.ARMOR_SECOND) {
            return new GlintVertexConsumer(buffer, GlintContext.armorLayer(true));
        }
        return buffer;
    }
}
//?}
