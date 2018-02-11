package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class LogOutCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult executionResult = new CommandResult(REDIRECT, JspResourceManager.INDEX_PAGE);
        executionResult.setSessionInvalidated(true);
        return executionResult;
    }
}
