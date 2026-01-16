package org.polyfrost.glintcolorizer.mixins.v1_21_6;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import org.polyfrost.glintcolorizer.util.GlintMetadata;
import org.polyfrost.glintcolorizer.util.ItemRenderStateStorage;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.world.entity.decoration.ItemFrame;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrameRenderer.class)
public abstract class MixinItemFrameRenderer_StoreGlintState {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemModelResolver;updateForNonLiving(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/Entity;)V")
    )
    private static void glintcolorizer$storeItemStack(ItemFrame entity, ItemFrameRenderState itemFrameRenderState, float partialTick, CallbackInfo ci) {
        ((ItemRenderStateStorage) itemFrameRenderState.item).glintcolorizer$setItemStack(entity.getItem());
        ((ItemRenderStateStorage) itemFrameRenderState.item).glintcolorizer$setRenderMode(GlintMetadata.RenderMode.FRAMED);
    }
}
//?} else {
/*@Mixin(ItemFrameRenderer.class)
public abstract class MixinItemFrameRenderer_StoreGlintState {}
*///?}