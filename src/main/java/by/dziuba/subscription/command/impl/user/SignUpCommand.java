package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.SignUpService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.SignUpServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class SignUpCommand implements Command {
    public static final String LOGIN_PARAMETER = "login";
    public static final String EMAIL_PARAMETER = "email";
    public static final String FIRST_NAME_PARAMETER = "firstName";
    public static final String LAST_NAME_PARAMETER = "lastName";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String CITY_PARAMETER = "city";
    public static final String ADDRESS_PARAMETER = "address";
    public static final String POSTAL_PARAMETER = "postal";
    public static final String USER_EXISTS_MESSAGE = "User already exists.";

    private final SignUpService signUpService = new SignUpServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        //TODO check email and overall signup process
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, JspResourceManager.LOGIN_PAGE_COMMAND);
            User user = setUserData(requestContent);
            if (userService.getUserByLogin(user.getLogin()) == null) {
                signUpService.signUp(user);
            } else {
                commandResult = new CommandResult(409, USER_EXISTS_MESSAGE);
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private User setUserData(RequestContent requestContent) {
        User user = new User();
        user.setLogin(requestContent.getRequestParameter(LOGIN_PARAMETER));
        user.setEmail(requestContent.getRequestParameter(EMAIL_PARAMETER));
        user.setFirstName(requestContent.getRequestParameter(FIRST_NAME_PARAMETER));
        user.setLastName(requestContent.getRequestParameter(LAST_NAME_PARAMETER));
        user.setCity(requestContent.getRequestParameter(CITY_PARAMETER));
        user.setAddress(requestContent.getRequestParameter(ADDRESS_PARAMETER));
        user.setPostalIndex(requestContent.getRequestParameter(POSTAL_PARAMETER));
        user.setAdmin(false);
        user.setBanned(false);
        user.setRegistrationDate(Date.valueOf(LocalDate.now()));
        user.setPassword(requestContent.getRequestParameter(PASSWORD_PARAMETER));
        user.setLoan(BigDecimal.valueOf(0));
        return user;
    }
}
