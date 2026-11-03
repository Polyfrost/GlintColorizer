package org.polyfrost.glintcolorizer.render

//? if modern {
import com.mojang.blaze3d.vertex.VertexConsumer

class GlintVertexConsumer(private val delegate: VertexConsumer?, private val layer: GlintLayer?) : VertexConsumer {
    private inline fun forward(action: VertexConsumer.(GlintLayer) -> Unit): VertexConsumer {
        if (delegate != null && layer != null) {
            delegate.action(layer)
        }
        return this
    }

    override fun addVertex(x: Float, y: Float, z: Float): VertexConsumer = forward {
        addVertex(x, y, z)
        setColor(it.red, it.green, it.blue, 255)
    }

    override fun setUv(u: Float, v: Float): VertexConsumer = forward { setUv(it.u(u, v), it.v(u, v)) }

    override fun setColor(red: Int, green: Int, blue: Int, alpha: Int): VertexConsumer = this

    //? if >=1.21.11 {
    override fun setColor(color: Int): VertexConsumer = this

    override fun setLineWidth(width: Float): VertexConsumer = this
    //?}

    override fun setUv1(u: Int, v: Int): VertexConsumer = forward { setUv1(u, v) }

    override fun setUv2(u: Int, v: Int): VertexConsumer = forward { setUv2(u, v) }

    //? if >=26.3 {
    override fun setUv3(u: Float, v: Float): VertexConsumer = this
    //?}

    override fun setNormal(x: Float, y: Float, z: Float): VertexConsumer = forward { setNormal(x, y, z) }

    companion object {
        @JvmField
        val NONE = GlintVertexConsumer(null, null)
    }
}
//?}
