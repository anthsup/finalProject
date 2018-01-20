package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.SignUpService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.SignUpServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;

public class SignUpCommand implements Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String EMAIL_PARAMETER = "email";
    private static final String FIRST_NAME_PARAMETER = "firstname";
    private static final String LAST_NAME_PARAMETER = "lastname";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_EXISTS_MESSAGE = "User already exists.";

    private final SignUpService signUpService = new SignUpServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult executionResult = new CommandResult();
            User user = new User();
            user.setLogin(requestContent.getRequestParameter(LOGIN_PARAMETER));
            user.setEmail(requestContent.getRequestParameter(EMAIL_PARAMETER));
            user.setFirstName(requestContent.getRequestParameter(FIRST_NAME_PARAMETER));
            user.setLastName(requestContent.getRequestParameter(LAST_NAME_PARAMETER));
            user.setPassword(requestContent.getRequestParameter(PASSWORD_PARAMETER));
            if (userService.getUserByLogin(user.getLogin()) == null) {
                signUpService.signUp(user);
                executionResult.setPage(JspResourceManager.LOGIN_PAGE);
            } else {
                executionResult.setErrorCode(409);
                executionResult.setErrorMessage(USER_EXISTS_MESSAGE);
            }
            return executionResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
