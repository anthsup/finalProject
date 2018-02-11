package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class ShowLogInCommand implements Command {
    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        return new CommandResult(REDIRECT, JspResourceManager.LOGIN_PAGE_COMMAND);
    }
}
