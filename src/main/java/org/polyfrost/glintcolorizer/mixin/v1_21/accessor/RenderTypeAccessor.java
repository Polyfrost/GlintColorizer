package org.polyfrost.glintcolorizer.mixin.v1_21.accessor;

import org.spongepowered.asm.mixin.Mixin;

//#if MC >=1.21
//$$ import com.mojang.blaze3d.vertex.VertexFormat;
//$$ import net.minecraft.client.renderer.RenderType;
//$$ import org.spongepowered.asm.mixin.gen.Invoker;
//#endif

//#if MC >=1.21
//$$ @Mixin(RenderType.class)
//#else
@Mixin(net.minecraft.client.Minecraft.class)
//#endif
public interface RenderTypeAccessor {
    //#if MC >=1.21
    //$$ @Invoker("create")
    //$$ static RenderType.CompositeRenderType glintcolorizer$createRenderType(
    //$$         String string,
    //#if MC < 1.21.5
    //$$         VertexFormat vertexFormat, VertexFormat.Mode mode,
    //#endif
    //$$         int size,
    //#if MC >=1.21.5
    //$$         RenderPipeline renderPipeline,
    //#endif
    //$$         RenderType.CompositeState compositeState
    //$$ ) {
    //$$     return null;
    //$$ }
    //#endif
}
