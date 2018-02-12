package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.LogInService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.LogInServiceImpl;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class LogInCommand implements Command {
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";

    private final LogInService logInService = new LogInServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            if (requestContent.getRequestParameter("previousURI") != null) {
                commandResult.setPage(JspResourceManager.PROFILE_PAGE_COMMAND);
            }

            String login = requestContent.getRequestParameter(LOGIN_PARAMETER);
            String password = requestContent.getRequestParameter(PASSWORD_PARAMETER);
            User user = logInService.logIn(login, password);
            if (user != null) {
                commandResult.putSessionAttribute("user", user);
            } else {
                commandResult.setErrorCode(401);
                commandResult.setErrorMessage("Incorrect login or password");
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
