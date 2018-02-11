package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class ChangeLocaleCommand implements Command {
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
        commandResult.putSessionAttribute("locale", requestContent.getRequestParameter("locale"));
        return commandResult;
    }
}
