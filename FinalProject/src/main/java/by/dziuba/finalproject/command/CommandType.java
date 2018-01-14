package by.dziuba.finalproject.command;

import by.dziuba.finalproject.command.impl.LogInCommand;
import by.dziuba.finalproject.command.impl.LogOutCommand;

public enum CommandType {
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
