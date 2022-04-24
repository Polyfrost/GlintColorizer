package cc.woverflow.glintcolorizer.command;

//#if MODERN==0
import cc.woverflow.glintcolorizer.GlintColorizer;
import cc.woverflow.onecore.utils.GuiUtils;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

public class GlintCommand extends Command {
    public GlintCommand() {
        super(GlintColorizer.ID, true);
    }

    @DefaultHandler
    public void handle() {
        GuiUtils.openScreen(GlintColorizer.config);
    }
}
//#endif