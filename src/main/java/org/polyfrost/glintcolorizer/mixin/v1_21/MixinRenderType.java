package org.polyfrost.glintcolorizer.mixin.v1_21;

import org.spongepowered.asm.mixin.Mixin;

//#if MC >=1.21
//$$ import net.minecraft.client.renderer.RenderType;
//#endif

//#if MC >=1.21
//$$ @Mixin(
//#if MC <=1.21.4
//$$     RenderType.class
//#else
//$$     RenderType.CompositeRenderType.class
//#endif
//$$ )
//#else
@Mixin(net.minecraft.client.Minecraft.class)
//#endif
public abstract class MixinRenderType {
}
