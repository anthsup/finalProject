package by.dziuba.subscription.command;

import by.dziuba.subscription.exception.BadRequestException;
import by.dziuba.subscription.exception.CommandException;

public interface Command {
    CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException;
}
