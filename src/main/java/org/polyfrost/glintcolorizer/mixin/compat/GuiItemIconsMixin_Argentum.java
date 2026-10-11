package org.polyfrost.glintcolorizer.mixin.compat;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.ItemStack;
import org.polyfrost.glintcolorizer.hook.RenderItemHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "dev.rdh.argentum.impl.render.gui.hud.item.GuiItemIcons", remap = false)
public class GuiItemIconsMixin_Argentum {

    @ModifyReturnValue(method = "canBake", at = @At("RETURN"), require = 0)
    private static boolean glintColorizer$shinyPotsStayVanilla(boolean original, ItemStack item) {
        return original && !RenderItemHook.INSTANCE.drawsShinyEffect(item);
    }

}
