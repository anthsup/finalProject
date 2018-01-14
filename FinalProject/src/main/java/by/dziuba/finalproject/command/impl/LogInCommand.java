package by.dziuba.finalproject.command.impl;

import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.exception.CommandException;
import by.dziuba.finalproject.command.util.JspResourceManager;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.RequestContent;
import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.LogInService;
import by.dziuba.finalproject.service.exception.ServiceException;
import by.dziuba.finalproject.service.impl.LogInServiceImpl;

public class LogInCommand implements Command {
    private static final String LOGIN_PARAMETER = "login_username";
    private static final String PASSWORD_PARAMETER = "login_password";

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
                commandResult.setPage(JspResourceManager.getProperty(JspResourceManager.LOGIN_PAGE_COMMAND));
                commandResult.setRedirected(true);
            } else {
                commandResult.setErrorCode(401);
                commandResult.setErrorMessage("incorrect login or password");
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
