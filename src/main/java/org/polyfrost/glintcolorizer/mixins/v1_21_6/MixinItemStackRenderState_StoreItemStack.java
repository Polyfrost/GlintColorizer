package org.polyfrost.glintcolorizer.mixins.v1_21_6;

import org.spongepowered.asm.mixin.Mixin;

//? >=1.21.6 {
import org.polyfrost.glintcolorizer.util.GlintMetadata;
import org.polyfrost.glintcolorizer.util.ItemRenderStateStorage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.item.ItemStackRenderState;

@Mixin(ItemStackRenderState.class)
public abstract class MixinItemStackRenderState_StoreItemStack implements ItemRenderStateStorage {
    @Unique
    private ItemStack glintcolorizer$stack = ItemStack.EMPTY;

    @Unique
    private GlintMetadata.RenderMode glintcolorizer$renderMode = null;

    @Override
    public ItemStack glintcolorizer$getItemStack() {
        return glintcolorizer$stack;
    }

    @Override
    public void glintcolorizer$setItemStack(ItemStack itemStack) {
        glintcolorizer$stack = itemStack;
    }

    @Override
    public GlintMetadata.RenderMode glintcolorizer$getRenderMode() {
        return glintcolorizer$renderMode == null ? GlintMetadata.getRenderMode() : glintcolorizer$renderMode;
    }

    @Override
    public void glintcolorizer$setRenderMode(GlintMetadata.RenderMode renderMode) {
        this.glintcolorizer$renderMode = renderMode;
    }

    @Inject(
			//? >=1.21.10 {
			method = "submit",
			//?} else {
			/*method = "render",
			*///?}
			at = @At("HEAD")
	)
    private void glintcolorizer$applyState(
			PoseStack poseStack,
			//? >=1.21.10 {
			net.minecraft.client.renderer.SubmitNodeCollector nodeCollector,
			//?} else {
			/*net.minecraft.client.renderer.MultiBufferSource bufferSource,
			*///?}
			int packedLight,
			int packedOverlay,
			//? >=1.21.10
			int outlineColor,
			CallbackInfo ci
	) {
		GlintMetadata.setRenderMode(this.glintcolorizer$getRenderMode());
        GlintMetadata.setItemStack(this.glintcolorizer$getItemStack());
    }
}
//?} else {
/*@Mixin(net.minecraft.client.Minecraft.class)
public abstract class MixinItemStackRenderState_StoreItemStack {}
*///?}