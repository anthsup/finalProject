package by.dziuba.finalproject.command.impl;

import by.dziuba.finalproject.command.util.JspResourceManager;
import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.RequestContent;

public class LogOutCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult executionResult = new CommandResult();
        executionResult.setSessionInvalidated(true);
        executionResult.setRedirected(true);
        executionResult.setPage(JspResourceManager.getProperty(JspResourceManager.LOGIN_PAGE_COMMAND));
        return executionResult;
    }
}
