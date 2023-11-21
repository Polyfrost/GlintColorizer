package org.polyfrost.glintcolorizer.mixin;

import org.polyfrost.glintcolorizer.RenderItemHook;
import org.polyfrost.glintcolorizer.config.GlintConfig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemPotion;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = {"org.polyfrost.overflowanimations.handlers.GlintHandler", "cc.polyfrost.overflowanimations.handlers.GlintHandler", "cc.woverflow.overflowanimations.GlintHandler"})
public class OverflowGlintHandlerMixin {

    @Dynamic("OverflowAnimations")
    @ModifyConstant(method = "renderGlint", constant = @Constant(intValue = -8372020))
    private static int modifyGlint(int constant) {
        return GlintConfig.color.getRGB();
    }

    @Dynamic("OverflowAnimations")
    @Redirect(method = "renderGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private static void shouldDepthFunc(int factor) {
        if (RenderItemHook.isRenderingGUI && RenderItemHook.itemStack.getItem() instanceof ItemPotion && GlintConfig.potionGlint) {
            return;
        }
        GlStateManager.depthFunc(factor);
    }
}
