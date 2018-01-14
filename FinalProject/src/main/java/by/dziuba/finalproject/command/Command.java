package by.dziuba.finalproject.command;

import by.dziuba.finalproject.command.exception.CommandException;

public interface Command {
    CommandResult execute(RequestContent requestContent) throws CommandException;
}
