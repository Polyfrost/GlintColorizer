package org.polyfrost.glintcolorizer.mixin.modern;

//? if modern {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
//? if <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?} else
import net.minecraft.resources.Identifier;
import org.polyfrost.glintcolorizer.render.GlintRenderTypes;
import org.polyfrost.glintcolorizer.render.GreyscaleGlintTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TextureManager.class)
public abstract class TextureManagerMixin_GreyscaleGlint {

    @ModifyExpressionValue(method = "getTexture", at = @At(value = "NEW", target = "net/minecraft/client/renderer/texture/SimpleTexture"))
    //? if <1.21.11 {
    /*private SimpleTexture glintColorizer$greyscaleGlint(SimpleTexture original, @Local(argsOnly = true) ResourceLocation id) {
    *///?} else
    private SimpleTexture glintColorizer$greyscaleGlint(SimpleTexture original, @Local(argsOnly = true) Identifier id) {
        return GlintRenderTypes.TEXTURE.equals(id) ? new GreyscaleGlintTexture() : original;
    }
}
//?}
