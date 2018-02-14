package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.util.DataValidator;
import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.SignUpService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.SignUpServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class SignUpCommand implements Command {

    private final SignUpService signUpService = new SignUpServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, JspPath.LOGIN_PAGE_COMMAND);
            User user = setUserData(requestContent);
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            if (!DataValidator.validateUser(user)) {
                String msg = MessageManager.getMessage(MessageConstant.INVALID_USER_DATA, locale);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            } else if (userService.getUserByLogin(user.getLogin()) != null) {
                String msg = MessageManager.getMessage(MessageConstant.LOGIN_EXISTS, locale);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            } else if (userService.getUserByEmail(user.getEmail()) != null) {
                String msg = MessageManager.getMessage(MessageConstant.EMAIL_EXISTS, locale);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            } else {
                signUpService.signUp(user);
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private User setUserData(RequestContent requestContent) {
        User user = new User();
        user.setLogin(requestContent.getRequestParameter(ParameterConstant.LOGIN));
        user.setEmail(requestContent.getRequestParameter(ParameterConstant.EMAIL));
        user.setFirstName(requestContent.getRequestParameter(ParameterConstant.FIRST_NAME));
        user.setLastName(requestContent.getRequestParameter(ParameterConstant.LAST_NAME));
        user.setCity(requestContent.getRequestParameter(ParameterConstant.CITY));
        user.setAddress(requestContent.getRequestParameter(ParameterConstant.ADDRESS));
        user.setPostalIndex(requestContent.getRequestParameter(ParameterConstant.POSTAL));
        user.setAdmin(false);
        user.setBanned(false);
        user.setRegistrationDate(Date.valueOf(LocalDate.now()));
        user.setPassword(requestContent.getRequestParameter(ParameterConstant.PASSWORD));
        user.setLoan(BigDecimal.valueOf(0));
        return user;
    }
}
