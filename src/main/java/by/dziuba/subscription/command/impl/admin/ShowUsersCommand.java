package by.dziuba.subscription.command.impl.admin;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import java.util.List;

public class ShowUsersCommand implements Command {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(JspPath.USERS_PAGE);
            List<User> users = userService.getAllUsers();
            commandResult.putRequestAttribute(ParameterConstant.USERS, users);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
