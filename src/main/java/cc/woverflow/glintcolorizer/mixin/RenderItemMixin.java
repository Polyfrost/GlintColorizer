package cc.woverflow.glintcolorizer.mixin;

import cc.woverflow.glintcolorizer.config.GlintConfig;
import cc.woverflow.onecore.utils.ColorUtils;
import net.minecraft.client.renderer.entity.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RenderItem.class)
public class RenderItemMixin {
    @ModifyConstant(method = "renderEffect", constant = @Constant(intValue = -8372020))
    private int modifyGlint(int constant) {
        return GlintConfig.chroma ? ColorUtils.timeBasedChroma() : GlintConfig.color.getRGB();
    }
}
