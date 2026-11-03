package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.4 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.ints.IntList;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}

@Mixin(ItemStackRenderState.LayerRenderState.class)
public abstract class LayerRenderStateMixin_PotionTints {

    //? if <26.1 {
    /*@ModifyReturnValue(method = "prepareTintLayers", at = @At("RETURN"))
    private int[] glintColorizer$markPotionTints(int[] tints) {
        GlintContext.markTints(tints, GlintContext.getResolvingPotion());
        return tints;
    }
    *///?} else {
    @Unique private boolean glintColorizer$potion;

    @ModifyReturnValue(method = "tintLayers", at = @At("RETURN"))
    private IntList glintColorizer$trackPotion(IntList tints) {
        glintColorizer$potion = GlintContext.getResolvingPotion();
        return tints;
    }

    @Inject(method = "clear", at = @At("HEAD"))
    private void glintColorizer$resetPotion(CallbackInfo ci) {
        glintColorizer$potion = false;
    }

    @WrapOperation(method = "submit", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/IntList;toArray([I)[I"))
    private int[] glintColorizer$markPotionTints(IntList list, int[] array, Operation<int[]> original) {
        int[] tints = original.call(list, array);
        GlintContext.markTints(tints, glintColorizer$potion);
        return tints;
    }
    //?}
}
//?}
