package org.polyfrost.glintcolorizer.mixin.modern;

//? if >=1.21.4 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import org.polyfrost.glintcolorizer.render.GlintContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin_ShinyPotions {

    @ModifyReturnValue(method = "hasFoil", at = @At("RETURN"))
    private boolean glintColorizer$shinyPotions(boolean original) {
        return original || GlintContext.forcesResolvingFoil((ItemStack) (Object) this);
    }
}
//?}
