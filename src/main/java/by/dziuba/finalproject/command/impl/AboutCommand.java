package by.dziuba.finalproject.command.impl;

import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.RequestContent;
import by.dziuba.finalproject.command.util.JspResourceManager;

public class AboutCommand implements Command {
    
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult CommandResult = new CommandResult();
        CommandResult.putSessionAttribute("lastPageURI", JspResourceManager.getProperty(JspResourceManager.ABOUT_COMMAND));
        CommandResult.setPage(JspResourceManager.getProperty(JspResourceManager.ABOUT_PAGE));
        return CommandResult;
    }
}