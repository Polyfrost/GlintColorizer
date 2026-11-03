package org.polyfrost.glintcolorizer.render

//? if modern {
import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.client.renderer.texture.SimpleTexture
import net.minecraft.server.packs.resources.ResourceManager
//? if <1.21.4 {
/*import java.io.IOException
import net.minecraft.resources.ResourceLocation
*///?} else {
import net.minecraft.client.renderer.texture.TextureContents
//?}
//? if >=1.21.4 && <1.21.11
//import net.minecraft.resources.ResourceLocation
//? if >=1.21.11
import net.minecraft.resources.Identifier
import kotlin.math.max

//? if <1.21.11 {
//private val VANILLA = ResourceLocation.withDefaultNamespace("textures/misc/enchanted_glint_item.png")
//?} else
private val VANILLA = Identifier.withDefaultNamespace("textures/misc/enchanted_glint_item.png")

class GreyscaleGlintTexture : SimpleTexture(GlintRenderTypes.TEXTURE) {
    //? if <1.21.4 {
    /*override fun getTextureImage(resourceManager: ResourceManager): TextureImage {
        val texture = TextureImage.load(resourceManager, VANILLA)
        try {
            greyscale(texture.image)
        } catch (ignored: IOException) {
        }
        return texture
    }
    *///?} else {
    override fun loadContents(resourceManager: ResourceManager): TextureContents {
        val contents = TextureContents.load(resourceManager, VANILLA)
        greyscale(contents.image())
        return contents
    }
    //?}
}

private fun greyscale(image: NativeImage) {
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            //? if <1.21.4 {
            /*image.setPixelRGBA(x, y, greyscale(image.getPixelRGBA(x, y)))
            *///?} else
            image.setPixel(x, y, greyscale(image.getPixel(x, y)))
        }
    }
}

private fun greyscale(pixel: Int): Int {
    val value = max(pixel shr 16 and 0xFF, max(pixel shr 8 and 0xFF, pixel and 0xFF))
    return pixel and 0xFF000000.toInt() or value * 0x010101
}
//?}
