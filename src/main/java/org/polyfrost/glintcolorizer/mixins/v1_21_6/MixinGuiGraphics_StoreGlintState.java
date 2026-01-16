package org.polyfrost.glintcolorizer.mixins.v1_21_6;

import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.8 {
import org.polyfrost.glintcolorizer.util.GlintMetadata;
import org.polyfrost.glintcolorizer.util.ItemRenderStateStorage;
import org.polyfrost.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;

//? >=1.21.9 {
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;
 //?} else {
/*import net.minecraft.client.renderer.item.ItemStackRenderState;
*///?}

@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics_StoreGlintState {
	@WrapOperation(
			method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
			at = @At(
					value = "NEW",
					target = "()Lnet/minecraft/client/renderer/item/TrackingItemStackRenderState;"
			)
	)
	private TrackingItemStackRenderState glintcolorizer$storeItemGui(Operation<TrackingItemStackRenderState> original, @Local(argsOnly = true) ItemStack itemStack) {
		TrackingItemStackRenderState itemStackRenderState = original.call();
		if (GlintColorizerConfig.enabled) {
			((ItemRenderStateStorage) itemStackRenderState).glintcolorizer$setItemStack(itemStack);
			((ItemRenderStateStorage) itemStackRenderState).glintcolorizer$setRenderMode(GlintMetadata.RenderMode.GUI);
		}

		return itemStackRenderState;
	}
}
//?} else {
/*@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics_StoreGlintState {}
*///?}
