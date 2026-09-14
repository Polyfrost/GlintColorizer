
package org.polyfrost.glintcolorizer.mixin;



import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(RenderEntityItem.class)
public class ItemEntityRendererMixin_HandleCameraTransforms {
    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms;applyTransform(Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V", ordinal = 0))
    private void glintcolorizer$setCameraTransform(EntityItem itemEntity, double d, double e, double f, float g, float h, CallbackInfo ci) {
        RenderItemHook.INSTANCE.setTransformType(ItemCameraTransforms.TransformType.GROUND);
    }
}
