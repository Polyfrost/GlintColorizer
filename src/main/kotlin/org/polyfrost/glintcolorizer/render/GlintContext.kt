package org.polyfrost.glintcolorizer.render

//? if modern {
import net.minecraft.client.Minecraft
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.PotionItem
import org.polyfrost.glintcolorizer.config.GlintConfig
import org.polyfrost.glintcolorizer.config.GlintEffectOptions
import java.util.Collections
import java.util.WeakHashMap
import java.util.function.Supplier
import kotlin.math.cos
import kotlin.math.sin

object GlintContext {
    private const val ITEM_SCALE = 8.0
    private const val ENTITY_SCALE = 0.16
    private const val ARMOR_SCALE = 1.0 / 3.0
    private const val FIRST_PERIOD = 3000.0
    private const val SECOND_PERIOD = 4873.0
    private const val ARMOR_PERIOD = 3_000_000L
    private const val MILLIS_PER_TICK = 50.0

    private val defaults = GlintEffectOptions()

    private val potionTints: MutableSet<IntArray> = Collections.newSetFromMap(WeakHashMap())

    private val captured = WeakHashMap<Any, Item>()

    private class Item(val context: ItemDisplayContext, val potion: Boolean, val potionColor: Int)

    private var item: Item? = null
    private var resolving: ItemStack? = null
    private var resolvingContext: ItemDisplayContext? = null

    @JvmStatic
    val replacesItemGlint: Boolean
        get() = GlintConfig.enabled && item != null

    @JvmStatic
    val replacesArmorGlint: Boolean
        get() = GlintConfig.enabled

    @JvmStatic
    val hidesArmorGlint: Boolean
        get() = GlintConfig.enabled && GlintConfig.armorGlintToggle

    @JvmStatic
    fun withItem(context: ItemDisplayContext, stack: ItemStack, action: Runnable) = withItem(itemOf(context, stack), action)

    private fun itemOf(context: ItemDisplayContext, stack: ItemStack) =
        Item(context, isPotion(stack), stack.get(DataComponents.POTION_CONTENTS)?.color ?: -1)

    @JvmStatic
    fun withItem(context: ItemDisplayContext, tints: IntArray, action: Runnable) =
        withItem(Item(context, tints in potionTints, tints.firstOrNull() ?: -1), action)

    @JvmStatic
    fun withSpecialItem(context: ItemDisplayContext, action: Runnable) = withItem(Item(context, false, -1), action)

    private fun withItem(next: Item, action: Runnable) = scoped(next) { action.run() }

    private inline fun <T> scoped(next: Item, action: () -> T): T {
        val previous = item
        item = next
        try {
            return action()
        } finally {
            item = previous
        }
    }

    @JvmStatic
    fun capture(pose: Any) {
        item?.let { captured[pose] = it }
    }

    @JvmStatic
    fun isCaptured(pose: Any): Boolean = pose in captured

    @JvmStatic
    fun <T> withCaptured(pose: Any, action: Supplier<T>): T {
        val capturedItem = captured[pose] ?: return action.get()
        return scoped(capturedItem) { action.get() }
    }

    @JvmStatic
    fun withResolving(stack: ItemStack, context: ItemDisplayContext, action: Runnable) {
        val previousStack = resolving
        val previousContext = resolvingContext
        resolving = stack
        resolvingContext = context
        try {
            action.run()
        } finally {
            resolving = previousStack
            resolvingContext = previousContext
        }
    }

    @JvmStatic
    fun isPotion(stack: ItemStack): Boolean =
        stack.item is PotionItem && stack.get(DataComponents.POTION_CONTENTS)?.hasEffects() == true

    @JvmStatic
    fun forcesFoil(stack: ItemStack, context: ItemDisplayContext?): Boolean =
        GlintConfig.enabled && GlintConfig.potionGlint && !GlintConfig.potionGlintForeground &&
            context == ItemDisplayContext.GUI && isPotion(stack)

    @JvmStatic
    fun drawsShinyEffect(stack: ItemStack): Boolean = GlintConfig.enabled && GlintConfig.potionGlint && isPotion(stack)

    @JvmStatic
    val shinyEffectOpaque: Boolean
        get() = GlintConfig.potionGlintOpaque

    @JvmStatic
    val shinyEffectSize: Int
        get() = if (GlintConfig.potionGlintSize) 20 else 16

    @JvmStatic
    fun shinyLayer(stack: ItemStack, second: Boolean): GlintLayer =
        scoped(itemOf(ItemDisplayContext.GUI, stack)) { itemLayer(second, false) }

    @JvmStatic
    fun forcesResolvingFoil(stack: ItemStack): Boolean = stack === resolving && forcesFoil(stack, resolvingContext)

    @JvmStatic
    val resolvingPotion: Boolean
        get() = resolving?.let(::isPotion) == true

    @JvmStatic
    fun markTints(tints: IntArray, potion: Boolean) {
        if (potion) potionTints.add(tints) else potionTints.remove(tints)
    }

    @JvmStatic
    fun itemLayer(second: Boolean, entityUv: Boolean): GlintLayer {
        val item = item
        val gui = item?.context == ItemDisplayContext.GUI
        val potion = gui && item.potion
        val options = when (item?.context) {
            ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
            ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
            ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
            ItemDisplayContext.THIRD_PERSON_RIGHT_HAND -> GlintConfig.heldItem
            ItemDisplayContext.GUI -> if (potion && GlintConfig.potionGlintBackground) GlintConfig.shinyPots else GlintConfig.guiItem
            ItemDisplayContext.GROUND -> GlintConfig.droppedItem
            ItemDisplayContext.FIXED -> GlintConfig.framedItem
            //? if >=1.21.11
            ItemDisplayContext.ON_SHELF -> GlintConfig.framedItem
            else -> defaults
        }
        val color = if (potion && GlintConfig.potionBasedColor) item.potionColor else strokeColor(options, !second)

        val period = if (second) SECOND_PERIOD else FIRST_PERIOD
        val cycle = (System.currentTimeMillis() * options.speed.toDouble()).toLong() % period.toLong() / period
        val scale = (if (entityUv) ENTITY_SCALE else ITEM_SCALE) * options.scale
        val angle = Math.toRadians((if (second) options.strokeRotTwo else options.strokeRotOne).toDouble())
        val shift = (if (second) -cycle else cycle) * options.scale
        return layer(scale * cos(angle), -scale * sin(angle), scale * sin(angle), scale * cos(angle), shift, 0.0, color)
    }

    @JvmStatic
    fun armorLayer(second: Boolean): GlintLayer {
        val ticks = System.currentTimeMillis() % ARMOR_PERIOD / MILLIS_PER_TICK
        val angle = Math.toRadians(if (second) -30.0 else 30.0)
        val offset = ticks * (if (second) 0.004 else 0.001) * 20.0
        val m01 = -ARMOR_SCALE * sin(angle)
        val m11 = ARMOR_SCALE * cos(angle)
        return layer(ARMOR_SCALE * cos(angle), m01, ARMOR_SCALE * sin(angle), m11, m01 * offset, m11 * offset, GlintConfig.armorColor.argb)
    }

    private fun strokeColor(options: GlintEffectOptions, first: Boolean): Int = when {
        !options.individualStrokes -> options.glintColor.argb
        first -> options.strokeOneColor.argb
        else -> options.strokeTwoColor.argb
    }

    private fun layer(m00: Double, m01: Double, m10: Double, m11: Double, tu: Double, tv: Double, color: Int): GlintLayer {
        val strength = Minecraft.getInstance().options.glintStrength().get()
        fun channel(shift: Int) = ((color shr shift and 0xFF) * strength).toInt()
        return GlintLayer(m00, m01, m10, m11, tu, tv, channel(16), channel(8), channel(0))
    }
}
//?}
