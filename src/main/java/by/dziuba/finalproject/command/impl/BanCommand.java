package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.UserServiceImpl;

public class BanCommand implements Command {
    private static final String USER_ID_PARAMETER = "user_id";

    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            int userId = Integer.parseInt(requestContent.getRequestParameter(USER_ID_PARAMETER));
            if (userService.getUserById(userId).isAdmin()) {
                throw new BadRequestException("Administrators can't be banned");
            }
            userService.banUser(userId);
//            commandResult.setPage(JspResourceManager.USER_INFO_COMMAND) + userId);
            //TODO fix when page is available
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
