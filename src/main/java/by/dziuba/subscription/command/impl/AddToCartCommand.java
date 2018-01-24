package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AddToCartCommand implements Command {
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            Periodical periodical = periodicalService.getById(Integer.parseInt(requestContent.getRequestParameter("id")));
            List<Periodical> cartPeriodicals = new ArrayList<>();
            if (requestContent.getSessionAttribute("cart_products") != null) {
                ((List<Periodical>)requestContent.getSessionAttribute("cart_products")).add(periodical);
            } else {
                cartPeriodicals.add(periodical);
                commandResult.putSessionAttribute("cart_products", cartPeriodicals);
            }
            commandResult.setPage(requestContent.getReferer());
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
