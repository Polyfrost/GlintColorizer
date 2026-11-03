package org.polyfrost.glintcolorizer.mixin.modern;

//? if modern && <26.2 {
/*import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.polyfrost.glintcolorizer.render.GlintVertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <1.21.11 {
//import net.minecraft.client.renderer.RenderType;
//?} else {
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?}

@Mixin(MultiBufferSource.BufferSource.class)
public abstract class BufferSourceMixin_SwapGlint implements MultiBufferSource {

    @Shadow
    public abstract void endBatch(RenderType renderType);

    @Inject(method = "getBuffer", at = @At("HEAD"), cancellable = true)
    private void glintColorizer$swapGlint(RenderType renderType, CallbackInfoReturnable<VertexConsumer> cir) {
        //? if <1.21.11 {
        /^RenderType armor = RenderType.armorEntityGlint();
        boolean translucent = renderType == RenderType.glintTranslucent();
        boolean item = renderType == RenderType.glint();
        boolean entity = renderType == RenderType.entityGlint()
                //? if =1.21.1
                //|| renderType == RenderType.entityGlintDirect()
        ;
        ^///?} else {
        RenderType armor = RenderTypes.armorEntityGlint();
        boolean translucent = renderType == RenderTypes.glintTranslucent();
        boolean item = renderType == RenderTypes.glint();
        boolean entity = renderType == RenderTypes.entityGlint();
        //?}
        if (renderType == armor) {
            if (GlintContext.getHidesArmorGlint()) {
                cir.setReturnValue(GlintVertexConsumer.NONE);
            } else if (GlintContext.getReplacesArmorGlint()) {
                cir.setReturnValue(VertexMultiConsumer.create(
                        new GlintVertexConsumer(getBuffer(GlintRenderTypes.ARMOR_FIRST), GlintContext.armorLayer(false)),
                        new GlintVertexConsumer(getBuffer(GlintRenderTypes.ARMOR_SECOND), GlintContext.armorLayer(true))
                ));
            }
        } else if ((item || entity || translucent) && GlintContext.getReplacesItemGlint()) {
            cir.setReturnValue(VertexMultiConsumer.create(
                    new GlintVertexConsumer(getBuffer(translucent ? GlintRenderTypes.ITEM_TRANSLUCENT_FIRST : GlintRenderTypes.ITEM_FIRST), GlintContext.itemLayer(false, entity)),
                    new GlintVertexConsumer(getBuffer(translucent ? GlintRenderTypes.ITEM_TRANSLUCENT_SECOND : GlintRenderTypes.ITEM_SECOND), GlintContext.itemLayer(true, entity))
            ));
        }
    }

    //? if <1.21.11 {
    /^@Inject(method = "endBatch(Lnet/minecraft/client/renderer/RenderType;)V", at = @At("TAIL"))
    ^///?} else
    @Inject(method = "endBatch(Lnet/minecraft/client/renderer/rendertype/RenderType;)V", at = @At("TAIL"))
    private void glintColorizer$endTranslucentGlint(RenderType renderType, CallbackInfo ci) {
        //? if <1.21.11 {
        /^RenderType translucent = RenderType.glintTranslucent();
        ^///?} else
        RenderType translucent = RenderTypes.glintTranslucent();
        if (renderType == translucent) {
            endBatch(GlintRenderTypes.ITEM_TRANSLUCENT_FIRST);
            endBatch(GlintRenderTypes.ITEM_TRANSLUCENT_SECOND);
        }
    }
}
*///?}
