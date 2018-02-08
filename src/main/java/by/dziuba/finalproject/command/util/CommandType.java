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
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    DELETE_PERIODICAL(new DeletePeriodicalCommand()),
    PERIODICAL_EDIT(new ShowPeriodicalEditCommand()),
    EDIT_PERIODICAL(new EditPeriodicalCommand()),
    ADMIN_PANEL(new ShowAdminPanelCommand()),
    ADD_GENRE(new AddGenreCommand()),
    ADD_AUTHOR(new AddAuthorCommand()),
    DELETE_AUTHORS(new DeleteAuthorsCommand()),
    DELETE_GENRES(new DeleteGenresCommand()),
    PERIODICAL_ADD(new ShowPeriodicalAddCommand()),
    ADD_PERIODICAL(new AddPeriodicalCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
