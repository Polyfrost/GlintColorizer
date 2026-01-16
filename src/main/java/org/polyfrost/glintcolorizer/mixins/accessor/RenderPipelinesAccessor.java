package org.polyfrost.glintcolorizer.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;

//? if >=1.21.6 {
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderPipelines.class)
public interface RenderPipelinesAccessor {
    //? if >=1.21.6 {
    @Accessor("MATRICES_PROJECTION_SNIPPET")
    //?} else
    /*@Accessor("MATRICES_COLOR_FOG_SNIPPET")*/
    static RenderPipeline.Snippet glintcolorizer$getMatricesColorFogSnippet() {
        return null;
    }
}
//?} else {
/*@Mixin(net.minecraft.client.Minecraft.class)
public interface RenderPipelinesAccessor {}
*///?}