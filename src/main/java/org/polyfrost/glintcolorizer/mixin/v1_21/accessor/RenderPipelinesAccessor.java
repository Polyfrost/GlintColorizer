package org.polyfrost.glintcolorizer.mixin.v1_21.accessor;

import org.spongepowered.asm.mixin.Mixin;

//#if MC >=1.21.5
//$$ import com.mojang.blaze3d.pipeline.RenderPipeline;
//$$ import org.spongepowered.asm.mixin.gen.Accessor;
//#endif

//#if MC >=1.21.5
//$$ @Mixin(net.minecraft.client.renderer.RenderPipelines.class)
//#else
@Mixin(net.minecraft.client.Minecraft.class)
//#endif
public interface RenderPipelinesAccessor {
    //#if MC >=1.21.5
    //#if MC >=1.21.6
    //$$ @Accessor("MATRICES_PROJECTION_SNIPPET")
    //#else
    //$$ @Accessor("MATRICES_COLOR_FOG_SNIPPET")
    //#endif
    //$$ static RenderPipeline.Snippet getMatricesColorFogSnippet() {
    //$$     return null;
    //$$ }
    //#endif
}
