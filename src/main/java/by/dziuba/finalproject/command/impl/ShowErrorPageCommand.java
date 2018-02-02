package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;

import javax.servlet.RequestDispatcher;

public class ShowErrorPageCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        CommandResult commandResult = new CommandResult();
        commandResult.setPage(JspResourceManager.ERROR_PAGE);
        return commandResult;
    }
}
