package org.polyfrost.glintcolorizer.mixins;

import org.polyfrost.glintcolorizer.util.GlintPipeline;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public abstract class MixinRenderBuffers_RegisterRenderTypes {
    @Inject(method = "put", at = @At("HEAD"))
    private static void glintcolorizer$addGlintLayers(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, RenderType renderType, CallbackInfo ci) {
        glintcolorizer$addRenderTypes(
                map,
                GlintPipeline.ITEM_GLINT_1ST_LAYER_RENDERTYPE,
                GlintPipeline.SHINY_ITEM_GLINT_1ST_LAYER_RENDERTYPE,
                GlintPipeline.ITEM_GLINT_2ND_LAYER_RENDERTYPE,
                GlintPipeline.SHINY_ITEM_GLINT_2ND_LAYER_RENDERTYPE,
                GlintPipeline.ARMOR_GLINT_1ST_LAYER_RENDERTYPE,
                GlintPipeline.ARMOR_GLINT_2ND_LAYER_RENDERTYPE
        );
    }

    @Unique
    private static void glintcolorizer$addRenderTypes(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, RenderType... renderTypes) {
        for (RenderType renderType : renderTypes) {
            if (!map.containsKey(renderType)) {
                map.put(renderType, new ByteBufferBuilder(renderType.bufferSize()));
            }
        }
    }
}
