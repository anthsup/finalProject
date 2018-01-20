package by.dziuba.subscription.command;

import by.dziuba.subscription.command.impl.*;

public enum CommandType {
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand()),
    SIGNUP(new SignUpCommand()),
    BAN_USER(new BanCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
