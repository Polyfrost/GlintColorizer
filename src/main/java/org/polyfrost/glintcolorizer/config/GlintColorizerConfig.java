package org.polyfrost.glintcolorizer.config;

import org.polyfrost.glintcolorizer.GlintColorizerConstants;
import org.polyfrost.glintcolorizer.config.category.*;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.Accordion;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;

public class GlintColorizerConfig extends Config {
	public static final GlintColorizerConfig INSTANCE = new GlintColorizerConfig();

	// General
	@Switch(title = "Enabled")
	public static boolean enabled = true;

	// Armor Glint
	@Accordion(title = "Armor Glint", index = 0)
	public static ArmorGlintCategory armorGlint = new ArmorGlintCategory();

	// Held Item Glint
	@Accordion(title = "Held Item Glint", index = 1)
	public static HeldItemGlintCategory heldItemGlint = new HeldItemGlintCategory();

	// Gui Item Glint
	@Accordion(title = "Gui Item Glint", index = 2)
	public static GuiItemGlintCategory guiItemGlint = new GuiItemGlintCategory();

	// Framed Item Glint
	@Accordion(title = "Framed Item Glint", index = 3)
	public static FramedItemGlintCategory framedItemGlint = new FramedItemGlintCategory();

	// Dropped Item Glint
	@Accordion(title = "Dropped Item Glint", index = 4)
	public static DroppedItemGlintCategory droppedItemGlint = new DroppedItemGlintCategory();

	// Shiny Pots
	@Accordion(title = "Shiny Pots", index = 5)
	public static ShinyPotsCategory shinyPots = new ShinyPotsCategory();

	public GlintColorizerConfig() {
		super(GlintColorizerConstants.ID + ".json", "/assets/" + GlintColorizerConstants.ID + "/" + GlintColorizerConstants.ID + "_dark.svg", GlintColorizerConstants.NAME, Category.QOL);
	}
}
