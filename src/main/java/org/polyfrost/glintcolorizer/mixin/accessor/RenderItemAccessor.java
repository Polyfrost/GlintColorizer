package org.polyfrost.glintcolorizer.mixin.accessor;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderItem.class)
public interface RenderItemAccessor {

    @Invoker void invokeRenderModel(IBakedModel model, int color);

}