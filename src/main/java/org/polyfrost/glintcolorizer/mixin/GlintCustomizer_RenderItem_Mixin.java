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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class GlintCustomizer_RenderItem_Mixin {

    @Inject(method = "renderEffect", at = @At("HEAD"))
    private void pushMatrix(IBakedModel model, CallbackInfo ci) {
        GlStateManager.pushMatrix();
    }

    @Inject(method = "renderEffect", at = @At("TAIL"))
    private void popMatrix(IBakedModel model, CallbackInfo ci) {
        GlStateManager.popMatrix();
    }

    @Redirect(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void modifyScale(float x, float y, float z) {
        if (!GlintConfig.INSTANCE.enabled) {
            GlStateManager.scale(x, y, z);
        } else {
            float scaleFactor = GlintMetadata.getRenderingOptions().getScale();
            GlStateManager.scale(x * scaleFactor, y * scaleFactor, z * scaleFactor);
        }
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"), index = 0)
    private float modifySpeed(float speed) {
        if (!GlintConfig.INSTANCE.enabled) {
            return speed;
        }
        return GlintMetadata.getRenderingOptions().getSpeed();
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 0), index = 0)
    private float modifyFirstStrokeRotation(float angle) {
        if (!GlintConfig.INSTANCE.enabled) {
            return angle;
        }
        return GlintMetadata.getRenderingOptions().getFirstStrokeRotation();
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 1), index = 0)
    private float modifySecondStrokeRotation(float angle) {
        if (!GlintConfig.INSTANCE.enabled) {
            return angle;
        }
        return GlintMetadata.getRenderingOptions().getSecondStrokeRotation();
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V", ordinal = 0), index = 1)
    private int modifyFirstStrokeColor(int color) {
        return GlintMetadata.getColor(color, true);
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V", ordinal = 1), index = 1)
    private int modifySecondStrokeColor(int color) {
        return GlintMetadata.getColor(color, false);
    }

}
