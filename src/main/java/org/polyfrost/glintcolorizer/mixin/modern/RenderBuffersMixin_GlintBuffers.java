package org.polyfrost.glintcolorizer.mixin.modern;

//? if modern && <26.2 {
/*import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.21.11 {
//import net.minecraft.client.renderer.RenderType;
//?} else {
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?}

@Mixin(RenderBuffers.class)
public abstract class RenderBuffersMixin_GlintBuffers {

    @Inject(method = "put", at = @At("HEAD"))
    private static void glintColorizer$addGlintBuffers(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, RenderType renderType, CallbackInfo ci) {
        //? if <1.21.11 {
        //RenderType glint = RenderType.glint();
        //?} else
        RenderType glint = RenderTypes.glint();
        if (renderType != glint) {
            return;
        }
        for (RenderType type : GlintRenderTypes.ALL) {
            if (!map.containsKey(type)) {
                map.put(type, new ByteBufferBuilder(type.bufferSize()));
            }
        }
    }
}
*///?}
