package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemRenderer.class, remap = false)
public class MixinItemRenderer {
    @Inject(method = "renderItem", at = @At("HEAD"))
    @SuppressWarnings("deprecation")
    private void glintcolorizer$captureCameraTransform(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, CallbackInfo ci) {
        GlintMetadata.RenderMode renderMode;
        switch (transform) {
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
}
