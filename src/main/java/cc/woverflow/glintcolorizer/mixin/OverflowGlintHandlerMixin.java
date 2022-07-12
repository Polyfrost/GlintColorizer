package cc.woverflow.glintcolorizer.mixin;

import cc.woverflow.glintcolorizer.RenderItemHook;
import cc.woverflow.glintcolorizer.config.GlintConfig;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.item.ItemPotion;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Pseudo
@Mixin(targets = "cc.woverflow.overflowanimations.GlintHandler")
public class OverflowGlintHandlerMixin {

    @Dynamic("OverflowAnimations")
    @ModifyConstant(method = "renderGlint", constant = @Constant(intValue = -8372020))
    private static int modifyGlint(int constant) {
        return GlintConfig.color.getRGB();
    }

    @Dynamic("OverflowAnimations")
    @WrapWithCondition(method = "renderGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private static boolean shouldDepthFunc(int factor) {
        return !RenderItemHook.isRenderingGUI || !(RenderItemHook.itemStack.getItem() instanceof ItemPotion) || !GlintConfig.potionGlint;
    }
}
