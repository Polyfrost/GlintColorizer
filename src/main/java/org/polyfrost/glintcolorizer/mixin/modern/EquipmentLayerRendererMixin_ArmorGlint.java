package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=26.2 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

//? if =26.2 {
/*import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
*///?} else {
import net.minecraft.client.renderer.texture.UvMapping;
import net.minecraft.resources.Identifier;
//?}

@Mixin(EquipmentLayerRenderer.class)
public abstract class EquipmentLayerRendererMixin_ArmorGlint {
    @Unique
    private static final String RENDER_LAYERS = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V";

    //? if =26.2 {
    /*@WrapOperation(method = RENDER_LAYERS, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private <S> void glintColorizer$armorGlint(OrderedSubmitNodeCollector collector, Model<? super S> model, S state, PoseStack poseStack, RenderType renderType, int light, int overlay, int color, TextureAtlasSprite sprite, int outlineColor, ModelFeatureRenderer.CrumblingOverlay crumbling, Operation<Void> original) {
        if (renderType != RenderTypes.armorEntityGlint() || !GlintContext.getReplacesArmorGlint()) {
            original.call(collector, model, state, poseStack, renderType, light, overlay, color, sprite, outlineColor, crumbling);
        } else if (!GlintContext.getHidesArmorGlint()) {
            original.call(collector, model, state, poseStack, GlintRenderTypes.ARMOR_FIRST, light, overlay, color, sprite, 0, crumbling);
            original.call(collector, model, state, poseStack, GlintRenderTypes.ARMOR_SECOND, light, overlay, color, sprite, 0, crumbling);
        }
    }
    *///?} else {
    @Unique private boolean glintColorizer$pendingGlint;

    @WrapOperation(method = RENDER_LAYERS, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;armorCutoutNoCullGlint(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType glintColorizer$plainArmor(Identifier texture, Operation<RenderType> original) {
        if (!GlintContext.getReplacesArmorGlint()) {
            return original.call(texture);
        }
        glintColorizer$pendingGlint = true;
        return RenderTypes.armorCutoutNoCull(texture);
    }

    @WrapOperation(method = RENDER_LAYERS, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/UvMapping;I)V"))
    private <S> void glintColorizer$armorGlint(OrderedSubmitNodeCollector collector, Model<? super S> model, S state, PoseStack poseStack, RenderType renderType, int light, int overlay, int color, UvMapping uvMapping, int outlineColor, Operation<Void> original) {
        boolean glint = glintColorizer$pendingGlint || renderType == RenderTypes.trimmedArmorGlint() && GlintContext.getReplacesArmorGlint();
        glintColorizer$pendingGlint = false;
        if (renderType != RenderTypes.trimmedArmorGlint() || !glint) {
            original.call(collector, model, state, poseStack, renderType, light, overlay, color, uvMapping, outlineColor);
        }
        if (glint && !GlintContext.getHidesArmorGlint()) {
            original.call(collector, model, state, poseStack, GlintRenderTypes.ARMOR_FIRST, light, overlay, -1, null, 0);
            original.call(collector, model, state, poseStack, GlintRenderTypes.ARMOR_SECOND, light, overlay, -1, null, 0);
        }
    }
    //?}
}
//?}
