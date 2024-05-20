package org.polyfrost.glintcolorizer.mixin;

import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
        value = ForgeHooksClient.class,
        remap = false
)
public class ForgeHooksClientMixin {

    @Inject(
            method = "handleCameraTransforms",
            at = @At(
                    value = "HEAD"
            )
    )
    private static void glintColorizer$setCameraTransform(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfoReturnable<IBakedModel> cir) {
        RenderItemHook.INSTANCE.setTransformType(cameraTransformType);
    }

}