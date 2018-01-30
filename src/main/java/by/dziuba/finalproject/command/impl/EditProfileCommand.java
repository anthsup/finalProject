package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import static by.dziuba.subscription.command.impl.ChangePasswordCommand.USER_SESSION_ATTRIBUTE;
import static by.dziuba.subscription.command.impl.SignUpCommand.*;

public class EditProfileCommand implements Command {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        //TODO success message, validation etc
        try {
            CommandResult commandResult = new CommandResult();
            User currentUser = setUpdatedUserData(requestContent);
            userService.updateUserInfo(currentUser);
            commandResult.setPage(JspResourceManager.PROFILE_PAGE_COMMAND);
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private User setUpdatedUserData(RequestContent requestContent) {
        User user = (User) requestContent.getSessionAttribute(USER_SESSION_ATTRIBUTE);
        user.setLogin(requestContent.getRequestParameter(LOGIN_PARAMETER));
        user.setEmail(requestContent.getRequestParameter(EMAIL_PARAMETER));
        user.setFirstName(requestContent.getRequestParameter(FIRST_NAME_PARAMETER));
        user.setLastName(requestContent.getRequestParameter(LAST_NAME_PARAMETER));
        user.setCity(requestContent.getRequestParameter(CITY_PARAMETER));
        user.setAddress(requestContent.getRequestParameter(ADDRESS_PARAMETER));
        user.setPostalIndex(requestContent.getRequestParameter(POSTAL_PARAMETER));
        return user;
    }
}
