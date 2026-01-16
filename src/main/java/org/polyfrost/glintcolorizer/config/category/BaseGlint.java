package org.polyfrost.glintcolorizer.config.category;

import org.polyfrost.oneconfig.api.config.v1.annotations.Color;
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;
import org.polyfrost.polyui.color.ColorUtils;
import org.polyfrost.polyui.color.PolyColor;

public class BaseGlint {
	@Switch(title = "Enabled")
	public boolean enabled = true;

	@Switch(title = "Individual Strokes")
	public boolean individualStrokes = false;

	@Color(title = "Color")
	public PolyColor color = ColorUtils.rgba(0x80, 0x40, 0xCC, 0xFF);

	@Color(title = "Stroke One Color")
	public PolyColor strokeOneColor = ColorUtils.rgba(0xFF, 0x00, 0x00, 0xFF);

	@Slider(title = "Stroke Two Rotation", min = -50, max = 50, step = 1)
	public int strokeOneRotation = -50;

	@Color(title = "Stroke Two Color")
	public PolyColor strokeTwoColor = ColorUtils.rgba(0x0A, 0xEA, 0xFF, 0xFF);

	@Slider(title = "Stroke Two Rotation", min = -50, max = 50, step = 1)
	public int strokeTwoRotation = 10;

	@Slider(title = "Speed", max = 2.0F, step = 0.1F)
	public float speed = 1.0F;

	@Slider(title = "Scale", max = 2.0F)
	public float scale = 1.0F;
}
