package org.polyfrost.glintcolorizer.mixin;

//? if modern {
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class GlintMixinPlugin implements IMixinConfigPlugin {

    @Override
    public List<String> getMixins() {
        List<String> mixins = new ArrayList<>();
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            return mixins;
        }
        mixins.add("modern.GuiGraphicsMixin_ShinyEffect");
        mixins.add("modern.TextureManagerMixin_GreyscaleGlint");
        //? if <26.1 {
        //mixins.add("modern.ItemRendererMixin_GlintContext");
        //?} else
        mixins.add("modern.ItemFeatureRendererMixin_GlintContext");
        //? if <26.2 {
        /*mixins.add("modern.BufferSourceMixin_SwapGlint");
        mixins.add("modern.RenderBuffersMixin_GlintBuffers");
        *///?} else {
        mixins.add("modern.EquipmentLayerRendererMixin_ArmorGlint");
        mixins.add("modern.RenderTypeFeatureRendererMixin_ArmorGlint");
        //?}
        //? if >=1.21.4 {
        mixins.add("modern.ItemModelResolverMixin_ShinyPotions");
        mixins.add("modern.ItemStackMixin_ShinyPotions");
        mixins.add("modern.LayerRenderStateMixin_PotionTints");
        mixins.add("modern.ItemStackRenderStateMixin_GlintContext");
        mixins.add("modern.ArmorGlintMixin_OverflowAnimations");
        //?}
        //? if >=1.21.9 {
        mixins.add("modern.PoseMixin_CaptureItem");
        mixins.add("modern.ModelFeatureRendererMixin_SpecialGlint");
        //?}
        //? if >=1.21.9 && <26.2
        //mixins.add("modern.ModelPartFeatureRendererMixin_SpecialGlint");
        //? if >=26.3 {
        mixins.add("modern.TridentSpecialRendererMixin_SpecialGlint");
        mixins.add("modern.ShieldSpecialRendererMixin_SpecialGlint");
        //?}
        return mixins;
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
//?}
