package org.polyfrost.glintcolorizer.mixin;

import org.polyfrost.glintcolorizer.GlintMetadata;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.ForgeHooksClient;
import org.polyfrost.glintcolorizer.GlintMetadata.RenderMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public class CaptureCameraTransform_ForgeHooksClient_Mixin {
    @Inject(method = "handleCameraTransforms", at = @At("HEAD"))
    @SuppressWarnings("deprecation")
    private static void captureCameraTransform(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfoReturnable<IBakedModel> cir) {
        switch (cameraTransformType) {
            case FIRST_PERSON:
            case THIRD_PERSON:
                GlintMetadata.setRenderMode(RenderMode.HELD);
                break;
            case FIXED:
                GlintMetadata.setRenderMode(RenderMode.FRAMED);
                break;
            case GROUND:
                GlintMetadata.setRenderMode(RenderMode.DROPPED);
                break;
            default:
                GlintMetadata.setRenderMode(RenderMode.GUI);
                break;
        }
    }
}
