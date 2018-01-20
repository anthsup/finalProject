package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.util.JspResourceManager;

public class LogOutCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult executionResult = new CommandResult();
        executionResult.setSessionInvalidated(true);
        executionResult.setRedirected(true);
        executionResult.setPage(JspResourceManager.LOGIN_PAGE_COMMAND);
        return executionResult;
    }
}
