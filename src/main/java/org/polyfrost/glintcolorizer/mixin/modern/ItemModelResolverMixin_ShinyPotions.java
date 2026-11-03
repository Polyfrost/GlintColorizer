package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.4 {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;

//? if >=1.21.9 {
import net.minecraft.world.entity.ItemOwner;
//?} else
//import net.minecraft.world.entity.LivingEntity;

@Mixin(ItemModelResolver.class)
public abstract class ItemModelResolverMixin_ShinyPotions {

    @WrapMethod(method = "appendItemLayers")
    private void glintColorizer$trackStack(
            ItemStackRenderState renderState,
            ItemStack stack,
            ItemDisplayContext context,
            Level level,
            //? if >=1.21.9 {
            ItemOwner owner,
            //?} else
            //LivingEntity owner,
            int seed,
            Operation<Void> original
    ) {
        GlintContext.withResolving(stack, context, () -> original.call(renderState, stack, context, level, owner, seed));
    }
}
//?}
