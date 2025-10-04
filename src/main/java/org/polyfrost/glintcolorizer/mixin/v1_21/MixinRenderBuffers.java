package org.polyfrost.glintcolorizer.mixin.v1_21;

import org.spongepowered.asm.mixin.Mixin;

//#if MC >=1.21
//$$ @Mixin(net.minecraft.client.renderer.RenderBuffers.class)
//#else
@Mixin(net.minecraft.client.Minecraft.class)
//#endif
public abstract class MixinRenderBuffers {
}
