package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.impl.UserServiceImpl;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

/**
 * Changes user's ban status the the opposite.
 */
public class ChangeBanStatusCommand implements Command {

    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            int userId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.USER_ID));
            User user = userService.getUserById(userId);

            if (user == null) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.USER_NOT_FOUND, locale));
            } else if (user.isAdmin()) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.CANT_BAN_ADMIN, locale));
            }

            boolean banStatus = !user.isBanned();
            userService.changeUserBanStatus(userId, banStatus);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
