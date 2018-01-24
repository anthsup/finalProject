package by.dziuba.subscription.command;

import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;

public interface Command {
    CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException;
}
