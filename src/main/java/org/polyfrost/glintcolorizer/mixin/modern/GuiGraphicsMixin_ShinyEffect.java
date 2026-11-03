package org.polyfrost.glintcolorizer.mixin.modern;

//? if modern {
import net.minecraft.world.item.ItemStack;
import org.polyfrost.glintcolorizer.render.ShinyEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if <1.21.6 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?} else {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
//?}
//? if >=1.21.6 && <26.1 {
/*import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiItemRenderState;
import net.minecraft.client.gui.render.state.GuiRenderState;
*///?} else if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.state.gui.GuiItemRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
//?}

//? if <26.1 {
/*@Mixin(GuiGraphics.class)
*///?} else
@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsMixin_ShinyEffect {

    //? if <1.21.6 {
    /*@Shadow @Final private PoseStack pose;
    @Shadow @Final private MultiBufferSource.BufferSource bufferSource;

    @Inject(
            method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER)
    )
    private void glintColorizer$drawShinyEffect(LivingEntity owner, Level level, ItemStack stack, int x, int y, int seed, int z, CallbackInfo ci) {
        ShinyEffect.draw(pose, bufferSource, stack);
    }
    *///?} else if <26.1 {
    /*@WrapOperation(
            method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiRenderState;submitItem(Lnet/minecraft/client/gui/render/state/GuiItemRenderState;)V")
    )
    private void glintColorizer$submitShinyEffect(GuiRenderState state, GuiItemRenderState item, Operation<Void> original, @Local(argsOnly = true) ItemStack stack) {
        ShinyEffect.submit(state, item, stack);
        original.call(state, item);
    }
    *///?} else {
    @WrapOperation(
            method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/state/gui/GuiRenderState;addItem(Lnet/minecraft/client/renderer/state/gui/GuiItemRenderState;)V")
    )
    private void glintColorizer$submitShinyEffect(GuiRenderState state, GuiItemRenderState item, Operation<Void> original, @Local(argsOnly = true) ItemStack stack) {
        ShinyEffect.submit(state, item, stack);
        original.call(state, item);
    }
    //?}
}
//?}
