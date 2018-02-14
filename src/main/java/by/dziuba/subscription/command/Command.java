package by.dziuba.subscription.command;

import by.dziuba.subscription.exception.BadRequestException;
import by.dziuba.subscription.exception.CommandException;

/**
 * This interface is a root element in the command hierarchy.
 */
public interface Command {
    CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException;
}
