package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddToCartCommand implements Command {
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        // TODO what happens if the same product are added?
        //TODO refactor quantities
        try {
            CommandResult commandResult = new CommandResult();
            Periodical periodical = periodicalService.getById(Integer.parseInt(requestContent.getRequestParameter("id")));
            Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute("quantities");
            if (quantities == null) {
                quantities = new HashMap<>();
            }
            quantities.put(periodical.getId(), 1);
            List<Periodical> cartPeriodicals = new ArrayList<>();
            if (requestContent.getSessionAttribute("cart_products") != null) {
                ((List<Periodical>)requestContent.getSessionAttribute("cart_products")).add(periodical);
            } else {
                cartPeriodicals.add(periodical);
                commandResult.putSessionAttribute("cart_products", cartPeriodicals);
            }
            commandResult.putSessionAttribute("totalPrice", calculateTotalPrice(requestContent, periodical));
            commandResult.putSessionAttribute("quantities", quantities);
            commandResult.setPage(requestContent.getReferer());
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private BigDecimal calculateTotalPrice(RequestContent requestContent, Periodical periodical) {
        BigDecimal totalPrice = (BigDecimal)requestContent.getSessionAttribute("totalPrice");
        if (totalPrice == null) {
            totalPrice = new BigDecimal(0);
        }
        return totalPrice.add(periodical.getPrice());
    }
}
