package org.polyfrost.glintcolorizer.mixins.v1_21_6;

import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import org.polyfrost.glintcolorizer.util.GlintMetadata;
import org.polyfrost.glintcolorizer.util.ItemRenderStateStorage;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer_StoreGlintState {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;extractItemGroupRenderState(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/item/ItemModelResolver;)V")
    )
    private static void glintcolorizer$storeItemStack(ItemEntity entity, ItemEntityRenderState itemEntityRenderState, float partialTick, CallbackInfo ci) {
        ((ItemRenderStateStorage) itemEntityRenderState.item).glintcolorizer$setItemStack(entity.getItem());
        ((ItemRenderStateStorage) itemEntityRenderState.item).glintcolorizer$setRenderMode(GlintMetadata.RenderMode.DROPPED);
    }
}
//?} else {
/*@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer_StoreGlintState {}
*///?}
