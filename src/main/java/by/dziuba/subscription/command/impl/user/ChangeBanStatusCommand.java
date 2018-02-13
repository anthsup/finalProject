package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.BadRequestException;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class ChangeBanStatusCommand implements Command {

    private final UserService userService = new UserServiceImpl();
    //TODO localize error msg
    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            int userId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.USER_ID));
            User user = userService.getUserById(userId);
            if (user.isAdmin()) {
                throw new BadRequestException("Administrators can't be banned");
            }
            boolean banStatus = !user.isBanned();
            userService.changeUserBanStatus(userId, banStatus);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
