package by.dziuba.subscription.command.util;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.impl.*;

public enum CommandType {
    LOGIN(new LogInCommand()),
    LOGOUT(new LogOutCommand()),
    SIGNUP(new SignUpCommand()),
    BAN_USER(new ChangeBanStatusCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    PERIODICALS(new ShowPeriodicalsCommand()),
    ADD_TO_CART(new AddToCartCommand()),
    SIGNUP_PAGE(new ShowSignUpCommand()),
    CART(new ShowCartCommand()),
    CHANGE_PRICE(new ChangePriceCommand()),
    DELETE_FROM_CART(new DeleteFromCartCommand()),
    CHECKOUT(new CheckoutCommand()),
    PROFILE(new ShowProfileCommand()),
    PROFILE_EDIT(new ShowProfileEditCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    USERS(new ShowUsersCommand()),
    ERROR(new ShowErrorPageCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
