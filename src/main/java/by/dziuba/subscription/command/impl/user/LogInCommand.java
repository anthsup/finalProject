package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.LogInService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.LogInServiceImpl;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class LogInCommand implements Command {
    private final LogInService logInService = new LogInServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            if (requestContent.getRequestParameter("previousURI") != null) {
                commandResult.setPage(JspPath.PROFILE_PAGE_COMMAND);
            }

            String login = requestContent.getRequestParameter(ParameterConstant.LOGIN);
            String password = requestContent.getRequestParameter(ParameterConstant.PASSWORD);
            User user = logInService.logIn(login, password);
            if (user != null) {
                commandResult.putSessionAttribute(ParameterConstant.USER, user);
            } else {
                commandResult = new CommandResult(401, "Incorrect login or password");
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
