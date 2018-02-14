package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.BadRequestException;
import by.dziuba.subscription.exception.CommandException;

public class ShowBanPageCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(JspPath.BAN_PAGE);
    }
}
