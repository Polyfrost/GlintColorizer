package cc.woverflow.glintcolorizer.mixin;

import cc.woverflow.glintcolorizer.RenderItemHook;
import cc.woverflow.glintcolorizer.config.GlintConfig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin {
    @Unique
    private static final String IBAKEDMODEL =
            //#if MC<11200
            "net/minecraft/client/resources/model/IBakedModel";
            //#else
            //$$ "net/minecraft/client/renderer/block/model/IBakedModel";
            //#endif

    @Unique
    private static final String RENDERITEM =
            //#if MC<11200
            "net/minecraft/client/renderer/entity/RenderItem";
            //#else
            //$$ "net/minecraft/client/renderer/RenderItem";
            //#endif

    @Unique
    private static final String renderItemIntoGui =
            //#if MC<11200
            "renderItemIntoGUI";
            //#else
            //$$ "renderItemModelIntoGUI";
            //#endif
    @Shadow
    protected abstract void renderEffect(IBakedModel model);

    @ModifyConstant(method = "renderEffect", constant = @Constant(intValue = -8372020))
    private int modifyGlint(int constant) {
        return GlintConfig.color.getRGB();
    }

    @Inject(method = renderItemIntoGui, at = @At("HEAD"))
    private void setGUIRenderingTrue(ItemStack stack, int x, int y,
                                     //#if MC>=10800
                                     //$$ IBakedModel model,
                                     //#endif
                                     CallbackInfo ci) {
        RenderItemHook.isRenderingGUI = true;
    }

    @Inject(method = renderItemIntoGui, at = @At("TAIL"))
    private void setGUIRenderingFalse(ItemStack stack, int x, int y,
                                      //#if MC>=10800
                                      //$$ IBakedModel model,
                                      //#endif
                                      CallbackInfo ci) {
        RenderItemHook.isRenderingGUI = false;
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;L" + IBAKEDMODEL + ";)V", at = @At("HEAD"))
    private void getItemRendering(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        RenderItemHook.itemStack = stack;
    }

    @Redirect(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthFunc(I)V"))
    private void shouldDepthFunc(int factor) {
        if (RenderItemHook.itemStack.getItem() instanceof ItemPotion && GlintConfig.potionGlint) {
            if (RenderItemHook.isRenderingGUI || GlintConfig.potionGlintType == 1) {
                return;
            }
        }
        GlStateManager.depthFunc(factor);
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;L" + IBAKEDMODEL + ";)V", at = @At(value = "INVOKE", target = "L" + RENDERITEM + ";renderModel(L" + IBAKEDMODEL + ";Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER))
    private void onRenderModel(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (stack.getItem() instanceof ItemPotion && GlintConfig.potionGlint && RenderItemHook.isRenderingGUI) {
            renderEffect(model);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;L" + IBAKEDMODEL + ";)V", at = @At(value = "INVOKE", target = "L" + RENDERITEM + ";renderEffect(L" + IBAKEDMODEL + ";)V"))
    private void shouldRender(RenderItem instance, IBakedModel model, ItemStack stack, IBakedModel model2) {
        if (RenderItemHook.isRenderingGUI && stack.getItem() instanceof ItemPotion && GlintConfig.potionGlint && GlintConfig.potionGlintType == 0) {
            return;
        }
        renderEffect(model);
    }
}
