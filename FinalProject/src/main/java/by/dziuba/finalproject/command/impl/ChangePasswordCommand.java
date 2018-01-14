package by.dziuba.finalproject.command.impl;

import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.RequestContent;
import by.dziuba.finalproject.command.exception.BadRequestException;
import by.dziuba.finalproject.command.exception.CommandException;
import by.dziuba.finalproject.command.util.JspResourceManager;
import by.dziuba.finalproject.entity.User;
import by.dziuba.finalproject.service.exception.ServiceException;
import by.dziuba.finalproject.service.impl.LogInServiceImpl;
import by.dziuba.finalproject.service.UserService;
import by.dziuba.finalproject.service.impl.UserServiceImpl;

public class ChangePasswordCommand implements Command {
    private static final int MIN_PASSWORD_LENGTH = 8;

    private static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String OLD_PASSWORD_PARAMETER = "old_password";
    private static final String NEW_PASSWORD_PARAMETER = "new_password";

    private final LogInService LogInService = new LogInServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult CommandResult = new CommandResult();
            User currentUser = (User) requestContent.getSessionAttribute(USER_SESSION_ATTRIBUTE);
            String oldPassword = requestContent.getRequestParameter(OLD_PASSWORD_PARAMETER);
            String newPassword = requestContent.getRequestParameter(NEW_PASSWORD_PARAMETER);
            if (!areParametersValid(requestContent)) {
                throw new BadRequestException("invalid parameters");
            }
            User user = LogInService.logIn(currentUser.getLogin(), oldPassword);
            if (user != null) {
                userService.updateUserPassword(user.getId(), newPassword);
                CommandResult.setPage(JspResourceManager.getProperty(JspResourceManager.USER_INFO_COMMAND) + currentUser.getId());
            } else {
                CommandResult.setErrorCode(401);
                CommandResult.setErrorMessage("old password doesn't match");
            }
            return CommandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
    private boolean areParametersValid(RequestContent requestContent){
        boolean valid = true;
        String password = requestContent.getRequestParameter(NEW_PASSWORD_PARAMETER);
        if (password == null || password.isEmpty() || password.length() < MIN_PASSWORD_LENGTH) {
            valid = false;
        }
        return valid;
    }
}
