package by.dziuba.subscription.command.util;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.impl.*;

public enum CommandType {
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand()),
    SIGNUP(new SignUpCommand()),
    BAN_USER(new BanCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    PERIODICALS(new ShowPeriodicalsCommand()),
    ADD_TO_CART(new AddToCartCommand()),
    SIGNUP_PAGE(new ShowSignUpCommand()),
    CART(new ShowCartCommand()),
    CHANGE_PRICE(new ChangePriceCommand()),
    DELETE_FROM_CART(new DeleteFromCartCommand()),
    CHECKOUT(new CheckoutCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
