package cc.woverflow.glintcolorizer.mixin;

import cc.woverflow.glintcolorizer.RenderItemHook;
import cc.woverflow.glintcolorizer.config.GlintConfig;
import cc.woverflow.onecore.utils.ColorUtils;
import gg.essential.lib.mixinextras.injector.WrapWithCondition;
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
    private int modifyGlint(int constant) {
        return GlintConfig.chroma ? ColorUtils.timeBasedChroma() : GlintConfig.color.getRGB();
    }

    @Dynamic("OverflowAnimations")
    @WrapWithCondition(method = "renderGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private boolean shouldDepthFunc(int factor) {
        return !RenderItemHook.isRenderingGUI || !(RenderItemHook.itemStack.getItem() instanceof ItemPotion) || !GlintConfig.potionGlint;
    }
}
