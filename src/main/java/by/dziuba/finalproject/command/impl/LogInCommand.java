package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.LogInService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.LogInServiceImpl;

public class LogInCommand implements Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";

    private final LogInService logInService = new LogInServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult();
            String login = requestContent.getRequestParameter(LOGIN_PARAMETER);
            String password = requestContent.getRequestParameter(PASSWORD_PARAMETER);
            User user = logInService.logIn(login, password);
            if (user != null) {
                commandResult.putSessionAttribute("user", user);
                commandResult.setPage(requestContent.getReferer());
                commandResult.setRedirected(true);
                //todo redirect
            } else {
                commandResult.setErrorCode(401);
                commandResult.putRequestAttribute("errorData", "Incorrect login or password");
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
