package org.polyfrost.glintcolorizer.mixins;

import org.polyfrost.glintcolorizer.config.GlintColorizerConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Item.class, priority = 999999)
public abstract class MixinItem_ApplyPotionGlint {
    @WrapOperation(method = "isFoil", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEnchanted()Z"))
    private boolean glintcolorizer$enablePotionGlint(ItemStack instance, Operation<Boolean> original) {
        boolean hasGlint = original.call(instance);
        if (GlintColorizerConfig.shinyPots.enabled && instance.getItem() instanceof PotionItem && !hasGlint) {
            PotionContents potionContents = instance.get(DataComponents.POTION_CONTENTS);
            if (potionContents != null) {
                hasGlint = potionContents.hasEffects();
            }
        }

        return hasGlint;
    }
}