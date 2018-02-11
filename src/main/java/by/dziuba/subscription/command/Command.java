package by.dziuba.subscription.command;

import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;

public interface Command {
    CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException;
}
