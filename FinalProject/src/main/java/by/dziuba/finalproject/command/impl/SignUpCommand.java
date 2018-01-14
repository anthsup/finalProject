package by.dziuba.finalproject.command.impl;

import by.dziuba.finalproject.command.util.JspResourceManager;
import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.RequestContent;
import by.dziuba.finalproject.command.exception.CommandException;
import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.SignUpService;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.service.exception.ServiceException;
import by.dziuba.finalproject.service.impl.SignUpServiceImpl;
import by.dziuba.finalproject.service.impl.UserServiceImpl;

public class SignUpCommand implements Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String EMAIL_PARAMETER = "email";
    private static final String FIRST_NAME_PARAMETER = "firstname";
    private static final String LAST_NAME_PARAMETER = "lastname";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_EXIST_MESSAGE = "User already exists.";

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
                executionResult.setPage(JspResourceManager.getProperty(JspResourceManager.LOGIN_PAGE_COMMAND));
            } else {
                executionResult.setErrorCode(409);
                executionResult.setErrorMessage(USER_EXIST_MESSAGE);
            }
            return executionResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
