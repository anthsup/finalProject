package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.util.DataValidator;
import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class EditProfileCommand implements Command {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        //TODO check redirected, validation etc
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, JspPath.PROFILE_PAGE_COMMAND);
            User currentUser = setUpdatedUserData(requestContent);
            if (!DataValidator.validateUser(currentUser)) {
                commandResult = new CommandResult(400, "Invalid format.");
            } else {
                userService.updateUserById(currentUser);
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private User setUpdatedUserData(RequestContent requestContent) {
        User user = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
        user.setLogin(requestContent.getRequestParameter(ParameterConstant.LOGIN));
        user.setEmail(requestContent.getRequestParameter(ParameterConstant.EMAIL));
        user.setFirstName(requestContent.getRequestParameter(ParameterConstant.FIRST_NAME));
        user.setLastName(requestContent.getRequestParameter(ParameterConstant.LAST_NAME));
        user.setCity(requestContent.getRequestParameter(ParameterConstant.CITY));
        user.setAddress(requestContent.getRequestParameter(ParameterConstant.ADDRESS));
        user.setPostalIndex(requestContent.getRequestParameter(ParameterConstant.POSTAL));
        user.setPhoto(requestContent.getRequestParameter(ParameterConstant.PHOTO));
        return user;
    }
}
