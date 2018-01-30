package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.LogInService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.LogInServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;

public class ChangePasswordCommand implements Command {
    private static final int MIN_PASSWORD_LENGTH = 6;

    public static final String USER_SESSION_ATTRIBUTE = "user";
    private static final String OLD_PASSWORD = "old_password";
    private static final String NEW_PASSWORD = "new_password";
    private static final String CONFIRM_PASSWORD = "confirm_password";

    private final LogInService logInService = new LogInServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            User currentUser = (User) requestContent.getSessionAttribute(USER_SESSION_ATTRIBUTE);
            String oldPassword = requestContent.getRequestParameter(OLD_PASSWORD);
            String newPassword = requestContent.getRequestParameter(NEW_PASSWORD);
            String confirmNewPassword = requestContent.getRequestParameter(CONFIRM_PASSWORD);
            if (!areParametersValid(requestContent)) {
                throw new BadRequestException("Invalid parameters.");
            }
            User user = logInService.logIn(currentUser.getLogin(), oldPassword);
            if (user != null && newPassword.equals(confirmNewPassword)) {
                userService.updateUserPassword(user.getId(), newPassword);
                commandResult.setPage(JspResourceManager.PROFILE_EDIT_COMMAND);
                commandResult.setRedirected(true);
                // TODO success message and validation
            } else {
                commandResult.setErrorCode(401);
                commandResult.setErrorMessage("Either old password is wrong or confirmation doesn't match the new one");
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private boolean areParametersValid(RequestContent requestContent){
        boolean valid = true;
        String password = requestContent.getRequestParameter(NEW_PASSWORD);
        if (password == null || password.isEmpty() || password.length() < MIN_PASSWORD_LENGTH) {
            valid = false;
        }
        return valid;
    }
}
