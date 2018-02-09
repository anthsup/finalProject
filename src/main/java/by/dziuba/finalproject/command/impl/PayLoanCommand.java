package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import java.math.BigDecimal;

public class PayLoanCommand implements Command {
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            User user = (User) requestContent.getSessionAttribute("user");
            user.setLoan(BigDecimal.valueOf(0));
            userService.updateLoan(user);
            commandResult.setPage(requestContent.getReferer());
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
