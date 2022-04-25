package cc.woverflow.glintcolorizer.mixin;

import cc.woverflow.glintcolorizer.config.GlintConfig;
import cc.woverflow.onecore.utils.ColorUtils;
import gg.essential.lib.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin {
    @Shadow protected abstract void renderEffect(IBakedModel model);
    private ItemStack itemStack = null;
    private boolean isRenderingGUI = false;

    @ModifyConstant(method = "renderEffect", constant = @Constant(intValue = -8372020))
    private int modifyGlint(int constant) {
        return GlintConfig.chroma ? ColorUtils.timeBasedChroma() : GlintConfig.color.getRGB();
    }

    @Inject(method = "renderItemIntoGUI", at = @At("HEAD"))
    private void setGUIRenderingTrue(ItemStack stack, int x, int y, CallbackInfo ci) {
        isRenderingGUI = true;
    }

    @Inject(method = "renderItemIntoGUI", at = @At("TAIL"))
    private void setGUIRenderingFalse(ItemStack stack, int x, int y, CallbackInfo ci) {
        isRenderingGUI = false;
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At("HEAD"))
    private void getItemRendering(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        itemStack = stack;
    }

    @WrapWithCondition(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private boolean shouldDepthFunc(int factor) {
        return !isRenderingGUI || !(itemStack.getItem() instanceof ItemPotion) || !GlintConfig.potionGlint;
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void onRenderModel(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (stack.getItem() instanceof ItemPotion && GlintConfig.potionGlint && isRenderingGUI) {
            renderEffect(model);
        }
    }

    @WrapWithCondition(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderEffect(Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    private boolean shouldRender(RenderItem instance, IBakedModel model, ItemStack stack, IBakedModel model2) {
        return !isRenderingGUI || !(stack.getItem() instanceof ItemPotion) || !GlintConfig.potionGlint;
    }
}
