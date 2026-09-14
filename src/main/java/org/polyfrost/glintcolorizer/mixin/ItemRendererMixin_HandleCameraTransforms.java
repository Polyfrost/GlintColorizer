package org.polyfrost.glintcolorizer.mixin;




import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(RenderItem.class)
public class ItemRendererMixin_HandleCameraTransforms {
    @Inject(method = "renderItemModelTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms;applyTransform(Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void glintcolorizer$setCameraTransform$hand(ItemStack item, IBakedModel model, ItemCameraTransforms.TransformType transform, CallbackInfo ci) {
        RenderItemHook.INSTANCE.setTransformType(transform);
    }

    @Inject(method = "renderItemIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms;applyTransform(Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void glintcolorizer$setCameraTransform$gui(ItemStack item, int x, int y, CallbackInfo ci) {
        RenderItemHook.INSTANCE.setTransformType(ItemCameraTransforms.TransformType.GUI);
    }
}
