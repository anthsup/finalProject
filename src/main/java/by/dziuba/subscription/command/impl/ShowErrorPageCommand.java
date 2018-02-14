package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.exception.BadRequestException;
import by.dziuba.subscription.exception.CommandException;

public class ShowErrorPageCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        return new CommandResult(JspPath.ERROR_PAGE);
    }
}
