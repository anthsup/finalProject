package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.LogInService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.LogInServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;
import by.dziuba.subscription.util.DataValidator;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class ChangePasswordCommand implements Command {
    private static final String OLD_PASSWORD = "old_password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String CONFIRM_PASSWORD = "confirm_password";

    private final LogInService logInService = new LogInServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            String oldPassword = requestContent.getRequestParameter(OLD_PASSWORD);
            String newPassword = requestContent.getRequestParameter(NEW_PASSWORD);
            String confirmNewPassword = requestContent.getRequestParameter(CONFIRM_PASSWORD);
            if (!DataValidator.validatePassword(newPassword) || !newPassword.equals(confirmNewPassword)) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_NEW_PASSWORD, locale));
            }
            User currentUser = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
            User user = logInService.logIn(currentUser.getLogin(), oldPassword);
            if (user != null) {
                userService.updateUserPassword(user.getId(), newPassword);
            } else {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.INVALID_OLD_PASSWORD, locale));
            }
            return new CommandResult(REDIRECT, JspPath.PROFILE_PAGE_COMMAND);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
