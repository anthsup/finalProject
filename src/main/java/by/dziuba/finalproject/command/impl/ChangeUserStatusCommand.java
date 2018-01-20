package by.dziuba.finalproject.command.impl;

import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.RequestContent;
import by.dziuba.finalproject.command.exception.CommandException;
import by.dziuba.finalproject.command.util.JspResourceManager;
import by.dziuba.finalproject.service.exception.ServiceException;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.service.impl.UserServiceImpl;

public class ChangeUserStatusCommand implements Command {
    private static final String USER_ID_PARAMETER = "user_id";
    private static final String STATUS_PARAMETER = "status";
    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult CommandResult = new CommandResult();
            int userId = Integer.parseInt(requestContent.getRequestParameter(USER_ID_PARAMETER));
            userService.updateUserStatus(userId, user);
            CommandResult.setPage(JspResourceManager.getProperty(JspResourceManager.USER_INFO_COMMAND) + userId);
            CommandResult.setRedirected(true);
            return CommandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
