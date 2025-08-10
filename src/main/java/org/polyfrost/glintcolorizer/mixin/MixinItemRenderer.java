package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderItem.class, remap = false)
public class MixinItemRenderer {
    @Inject(method = "renderItemModelTransform", at = @At("HEAD"))
    @SuppressWarnings("deprecation")
    private void glintcolorizer$captureCameraTransform(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        GlintMetadata.RenderMode renderMode;
        switch (cameraTransformType) {
            case FIRST_PERSON:
            case THIRD_PERSON: {
                renderMode = GlintMetadata.RenderMode.HELD;
                break;
            }

            case FIXED: {
                renderMode = GlintMetadata.RenderMode.FRAMED;
                break;
            }

            case GROUND: {
                renderMode = GlintMetadata.RenderMode.DROPPED;
                break;
            }

            default: {
                renderMode = GlintMetadata.RenderMode.GUI;
                break;
            }
        }

        GlintMetadata.setRenderMode(renderMode);
    }

    @Inject(method = "renderItemIntoGUI", at = @At("HEAD"))
    private void glintcolorizer$guiTransform(ItemStack stack, int x, int y, CallbackInfo ci) {
        GlintMetadata.setRenderMode(GlintMetadata.RenderMode.GUI);
    }
}
