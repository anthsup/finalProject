package by.dziuba.subscription.command;

import by.dziuba.subscription.command.impl.ChangeLocaleCommand;
import by.dziuba.subscription.command.impl.admin.ShowAdminPanelCommand;
import by.dziuba.subscription.command.impl.admin.ShowUsersCommand;
import by.dziuba.subscription.command.impl.cart.*;
import by.dziuba.subscription.command.impl.periodical.*;
import by.dziuba.subscription.command.impl.user.*;

public enum CommandType {
    // Guest Commands
    LOGIN(new LogInCommand()),
    LOGIN_PAGE(new ShowLogInCommand()),
    SIGNUP_PAGE(new ShowSignUpCommand()),
    SIGNUP(new SignUpCommand()),
    PERIODICALS(new ShowPeriodicalsCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    SEARCH_PERIODICALS(new SearchPeriodicalsCommand()),

    // User Commands
    LOGOUT(new LogOutCommand()),
    ADD_TO_CART(new AddToCartCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    PAY_LOAN(new PayLoanCommand()),
    CART(new ShowCartCommand()),
    DELETE_FROM_CART(new DeleteFromCartCommand()),
    CHANGE_PRICE(new ChangePriceCommand()),
    CHECKOUT(new CheckoutCommand()),
    PROFILE(new ShowProfileCommand()),
    PROFILE_EDIT(new ShowProfileEditCommand()),
    EDIT_PROFILE(new EditProfileCommand()),

    // Admin Commands
    BAN_USER(new ChangeBanStatusCommand()),
    USERS(new ShowUsersCommand()),
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
