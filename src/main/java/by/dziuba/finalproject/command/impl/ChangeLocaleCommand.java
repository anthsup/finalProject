package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;

public class ChangeLocaleCommand implements Command {
    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        CommandResult commandResult = new CommandResult();
        commandResult.putSessionAttribute("locale", requestContent.getRequestParameter("locale"));
        commandResult.setRedirected(true);
        commandResult.setPage(requestContent.getReferer());
        return commandResult;
    }
}
