package by.dziuba.finalproject.command.impl;

import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.RequestContent;
import by.dziuba.finalproject.command.exception.BadRequestException;
import by.dziuba.finalproject.command.exception.CommandException;
import by.dziuba.finalproject.command.util.JspResourceManager;
import by.dziuba.finalproject.service.exception.ServiceException;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.service.impl.UserServiceImpl;

import java.sql.Timestamp;

public class BanCommand implements Command {
    private static final String USER_ID_PARAMETER = "user_id";
    private static final String BAN_TIME_PARAMETER = "ban_time";

    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult CommandResult = new CommandResult();
            long banTime = Long.parseLong(requestContent.getRequestParameter(BAN_TIME_PARAMETER));
            int userId = Integer.parseInt(requestContent.getRequestParameter(USER_ID_PARAMETER));
            if (userService.getUserById(userId).isAdmin) {
                throw new BadRequestException("Administrator can't be banned");
            }
            userService.banUser(userId, new Timestamp(banTime));
            CommandResult.setPage(JspResourceManager.getProperty(JspResourceManager.USER_INFO_COMMAND) + userId);
            CommandResult.setRedirected(true);
            return CommandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
