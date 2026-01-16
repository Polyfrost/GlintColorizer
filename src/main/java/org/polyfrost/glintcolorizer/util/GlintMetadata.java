package org.polyfrost.glintcolorizer.util;

import org.polyfrost.glintcolorizer.config.GlintColorizerConfig;
import org.polyfrost.glintcolorizer.config.category.BaseGlint;
import org.polyfrost.glintcolorizer.config.category.ShinyPotsCategory;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;

import java.util.Objects;

public class GlintMetadata {
	public enum RenderMode {
		HELD,
		SHINY,
		GUI,
		DROPPED,
		FRAMED
	}

	private static RenderMode cachedRenderMode = RenderMode.HELD;
	private static ItemStack cachedItemStack = ItemStack.EMPTY;

	public static void setRenderMode(RenderMode renderMode) {
		if (!(renderMode == RenderMode.GUI && cachedRenderMode == RenderMode.SHINY)) {
			// preserve shiny render mode !
			cachedRenderMode = renderMode;
		}
	}

	public static RenderMode getRenderMode() {
		return cachedRenderMode;
	}

	public static void setItemStack(ItemStack itemStack) {
		if (!ItemStack.matches(itemStack, cachedItemStack)) {
			cachedItemStack = itemStack;
		}
	}

	public static ItemStack getItemStack() {
		return cachedItemStack;
	}

	public static BaseGlint getRenderingOptions() {
		return switch (cachedRenderMode) {
			case HELD -> GlintColorizerConfig.heldItemGlint;
			case SHINY -> GlintColorizerConfig.shinyPots;
			case GUI -> GlintColorizerConfig.guiItemGlint;
			case DROPPED -> GlintColorizerConfig.droppedItemGlint;
			case FRAMED -> GlintColorizerConfig.framedItemGlint;
		};
	}

	public static int getGlintColor(GlintLayer layer, boolean isArmor) {
		BaseGlint options = isArmor ? GlintColorizerConfig.armorGlint : getRenderingOptions();
		if (cachedItemStack.getItem() instanceof PotionItem && GlintColorizerConfig.shinyPots.useCustomColor) {
			options = GlintColorizerConfig.shinyPots;
			if (options instanceof ShinyPotsCategory shinyPotsCategory && shinyPotsCategory.usePotionBasedColor && cachedItemStack.has(DataComponents.POTION_CONTENTS)) {
				return Objects.requireNonNull(cachedItemStack.getComponents().get(DataComponents.POTION_CONTENTS)).getColor();
			}
		}

		return (options.individualStrokes
				? (layer == GlintLayer.FIRST ? options.strokeOneColor : options.strokeTwoColor)
				: options.color).getArgb();
	}
}