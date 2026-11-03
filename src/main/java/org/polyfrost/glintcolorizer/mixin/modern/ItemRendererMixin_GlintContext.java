package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21 && <26.1 {
/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;

//? if =1.21.1 {
/^import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
^///?} else {
import net.minecraft.client.renderer.item.ItemStackRenderState;
//?}
//? if =1.21.4
//import net.minecraft.client.resources.model.BakedModel;
//? if >=1.21.5 {
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
//?}
//? if >1.21.1 && <1.21.11
//import net.minecraft.client.renderer.RenderType;
//? if >=1.21.11
import net.minecraft.client.renderer.rendertype.RenderType;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin_GlintContext {

    //? if =1.21.1 {
    /^@WrapMethod(method = "render")
    private void glintColorizer$trackItem(ItemStack stack, ItemDisplayContext context, boolean leftHand, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay, BakedModel model, Operation<Void> original) {
        GlintContext.withItem(context, stack, () -> original.call(stack, context, leftHand, poseStack, buffers, light, overlay, model));
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hasFoil()Z"))
    private boolean glintColorizer$shinyPotions(ItemStack stack, Operation<Boolean> original, @Local(argsOnly = true) ItemDisplayContext context) {
        return original.call(stack) || GlintContext.forcesFoil(stack, context);
    }
    ^///?} else {
    @WrapMethod(method = "renderItem")
    private static void glintColorizer$trackItem(
            ItemDisplayContext context,
            PoseStack poseStack,
            MultiBufferSource buffers,
            int light,
            int overlay,
            int[] tints,
            //? if =1.21.4 {
            //BakedModel model,
            //?} else
            List<BakedQuad> model,
            RenderType renderType,
            ItemStackRenderState.FoilType foilType,
            Operation<Void> original
    ) {
        GlintContext.withItem(context, tints, () -> original.call(context, poseStack, buffers, light, overlay, tints, model, renderType, foilType));
    }
    //?}
}
*///?}
