package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import java.math.BigDecimal;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class PayLoanCommand implements Command {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            User user = (User) requestContent.getSessionAttribute(ParameterConstant.USER);
            user.setLoan(BigDecimal.valueOf(0));
            userService.updateLoan(user);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
