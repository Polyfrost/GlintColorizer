package org.polyfrost.glintcolorizer.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.polyfrost.glintcolorizer.GlintMetadata;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class GlintCustomizer_RenderItem_Mixin {
    @Inject(method = "renderEffect", at = @At("HEAD"))
    private void glintcolorizer$pushMatrix(IBakedModel model, CallbackInfo ci) {
        GlStateManager.pushMatrix();
    }

    @Inject(method = "renderEffect", at = @At("TAIL"))
    private void glintcolorizer$popMatrix(IBakedModel model, CallbackInfo ci) {
        GlStateManager.popMatrix();
    }

//    @WrapOperation(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
//    private void glintcolorizer$modifyScale(float x, float y, float z, Operation<Void> original) {
//        if (GlintConfig.INSTANCE.enabled) {
//            float scaleFactor = GlintMetadata.getRenderingOptions().getScale();
//            x *= scaleFactor;
//            y *= scaleFactor;
//            z *= scaleFactor;
//        }
//
//        original.call(x, y, z);
//    }

    //    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"), index = 0)
//    private float glintcolorizer$modifySpeed(float speed) {
//        if (GlintConfig.INSTANCE.enabled) {
//            return GlintMetadata.getRenderingOptions().getSpeed();
//        } else {
//            return speed;
//        }
//    }
//
//    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
//    private float glintcolorizer$modifyFirstStrokeRotation(float angle) {
//        if (GlintConfig.INSTANCE.enabled) {
//            return GlintMetadata.getRenderingOptions().getFirstStrokeRotation();
//        } else {
//            return angle;
//        }
//    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float glintcolorizer$modifySecondStrokeRotation(float angle) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getRenderingOptions().getSecondStrokeRotation();
        } else {
            return angle;
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V", ordinal = 0), index = 1)
    private int glintcolorizer$modifyFirstStrokeColor(int color) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getColor(true);
        } else {
            return color;
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V", ordinal = 1), index = 1)
    private int glintcolorizer$modifySecondStrokeColor(int color) {
        if (GlintConfig.INSTANCE.enabled) {
            return GlintMetadata.getColor(false);
        } else {
            return color;
        }
    }
}
