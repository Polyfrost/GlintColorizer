package org.polyfrost.glintcolorizer;

import org.polyfrost.glintcolorizer.config.GlintColorizerConfig;
import com.mojang.brigadier.Command;
import dev.deftu.omnicore.api.OmniResourceLocation;
import dev.deftu.omnicore.api.client.commands.OmniClientCommands;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.polyfrost.oneconfig.utils.v1.dsl.ScreensKt;

@Entrypoint
public final class GlintColorizer implements ClientModInitializer {
	public static ResourceLocation id(String path) {
		return OmniResourceLocation.createOrThrow(GlintColorizerConstants.ID, path);
	}

	@Override
	public void onInitializeClient() {
		// Config
		GlintColorizerConfig.INSTANCE.preload();

		// Commands
		OmniClientCommands.register(OmniClientCommands.literal(GlintColorizerConstants.ID)
				.executes((context) -> {
					ScreensKt.openUI(GlintColorizerConfig.INSTANCE);
					return Command.SINGLE_SUCCESS;
				})
				.build());
	}
}
