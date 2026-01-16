package org.polyfrost.glintcolorizer.mixins.v1_21_4;

import org.spongepowered.asm.mixin.Mixin;

import org.polyfrost.glintcolorizer.util.GlintMetadata;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class MixinItemModelResolver_StoreItemStack {
    @Inject(method = "appendItemLayers", at = @At("HEAD"))
    private void glintcolorizer$storeItemStack(
            ItemStackRenderState renderState,
            ItemStack stack,
            ItemDisplayContext displayContext,
            Level level,
            //? >=1.21.9 {
            net.minecraft.world.entity.ItemOwner itemOwner,
			 //?} else {
            /*net.minecraft.world.entity.LivingEntity entity,
            *///?}
            int seed,
            CallbackInfo ci
    ) {
        final boolean shouldApply =
                //? >=1.21.6 {
                displayContext != ItemDisplayContext.GUI;
				//?} else {
                /*true;
				*///?}
        if (shouldApply) {
            GlintMetadata.setItemStack(stack);
        }
    }
}
