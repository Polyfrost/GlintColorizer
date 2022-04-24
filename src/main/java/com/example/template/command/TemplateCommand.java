package com.example.template.command;

//#if MODERN==0
import cc.woverflow.onecore.utils.GuiUtils;
import com.example.template.ModTemplate;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;

public class TemplateCommand extends Command {
    public TemplateCommand() {
        super(ModTemplate.ID, true);
    }

    @DefaultHandler
    public void handle() {
        GuiUtils.openScreen(ModTemplate.config);
    }
}
//#endif