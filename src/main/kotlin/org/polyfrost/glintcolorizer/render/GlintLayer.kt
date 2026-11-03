package org.polyfrost.glintcolorizer.render

//? if modern {
class GlintLayer(
    m00: Double,
    m01: Double,
    m10: Double,
    m11: Double,
    tu: Double,
    tv: Double,
    internal val red: Int,
    internal val green: Int,
    internal val blue: Int,
) {
    private val m00 = m00.toFloat()
    private val m01 = m01.toFloat()
    private val m10 = m10.toFloat()
    private val m11 = m11.toFloat()
    private val tu = tu.toFloat()
    private val tv = tv.toFloat()

    internal fun u(u: Float, v: Float): Float = m00 * u + m01 * v + tu

    internal fun v(u: Float, v: Float): Float = m10 * u + m11 * v + tv
}
//?}
