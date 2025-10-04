package org.polyfrost.glintcolorizer.mixin.v1_21.accessor;

//#if MC >=1.21
//$$ import net.minecraft.client.renderer.RenderStateShard;
//$$ import net.minecraft.client.renderer.RenderType;
//$$ import org.spongepowered.asm.mixin.gen.Invoker;
//#endif

import org.spongepowered.asm.mixin.Mixin;

//#if MC >=1.21
//$$ @Mixin(RenderType.CompositeState.CompositeStateBuilder.class)
//#else
@Mixin(net.minecraft.client.Minecraft.class)
//#endif
public interface RenderTypeCompositeStateBuilderAccessor {
    //$$ @Invoker("setTextureState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withTextureState(RenderStateShard.EmptyTextureStateShard emptyTextureStateShard);

    //#if MC < 1.21.5
    //$$ @Invoker("setShaderState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withShaderState(RenderStateShard.ShaderStateShard shaderStateShard);

    //$$ @Invoker("setDepthTestState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withDepthTestState(RenderStateShard.DepthTestStateShard depthTestStateShard);

    //$$ @Invoker("setTransparencyState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withBlendState(RenderStateShard.TransparencyStateShard transparencyStateShard);

    //$$ @Invoker("setCullState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withCullState(RenderStateShard.CullStateShard cullStateShard);

    //$$ @Invoker("setWriteMaskState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withWriteMaskState(RenderStateShard.WriteMaskStateShard writeMaskStateShard);
    //#endif

    //$$ @Invoker("setLayeringState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withLayeringState(RenderStateShard.LayeringStateShard layeringStateShard);

    //$$ @Invoker("setTexturingState")
    //$$ RenderType.CompositeState.CompositeStateBuilder withTexturingState(RenderStateShard.TexturingStateShard texturingStateShard);

    //$$ @Invoker("createCompositeState")
    //$$ RenderType.CompositeState buildCompositeState(boolean affectsOutline);
}
