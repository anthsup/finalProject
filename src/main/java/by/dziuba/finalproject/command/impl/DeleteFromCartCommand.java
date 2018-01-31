package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class DeleteFromCartCommand implements Command {
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        //TODO error when cart is empty and delete pressed?
        try {
            CommandResult commandResult = new CommandResult();
            Periodical periodical = periodicalService.getById(Integer.parseInt(requestContent.getRequestParameter("id")));
            Set<Periodical> periodicals = (Set<Periodical>)requestContent.getSessionAttribute("cart_products");
            periodicals.remove(periodical);
            BigDecimal totalPrice = calculateTotalPrice(requestContent, periodical);

            commandResult.putSessionAttribute("totalPrice", totalPrice);
            commandResult.setPage(requestContent.getReferer());
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private BigDecimal calculateTotalPrice(RequestContent requestContent, Periodical periodical) {
        BigDecimal totalPrice = (BigDecimal)requestContent.getSessionAttribute("totalPrice");
        Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute("quantities");
        int quantity = quantities.get(periodical.getId());
        totalPrice = totalPrice.subtract(periodical.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return totalPrice;
    }
}
