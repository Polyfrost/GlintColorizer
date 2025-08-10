package org.polyfrost.glintcolorizer.mixin.accessor;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderItem.class)
public interface RenderModelAccessor {
    @Invoker("renderModel") // NOTE: It is used, it just doesn't see kotlin
    void glintcolorizer$renderModel(IBakedModel model, int color);
}
