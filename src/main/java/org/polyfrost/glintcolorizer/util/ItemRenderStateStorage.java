package org.polyfrost.glintcolorizer.util;

import net.minecraft.world.item.ItemStack;

public interface ItemRenderStateStorage {
    ItemStack glintcolorizer$getItemStack();

    void glintcolorizer$setItemStack(ItemStack itemStack);

    GlintMetadata.RenderMode glintcolorizer$getRenderMode();

    void glintcolorizer$setRenderMode(GlintMetadata.RenderMode renderMode);
}