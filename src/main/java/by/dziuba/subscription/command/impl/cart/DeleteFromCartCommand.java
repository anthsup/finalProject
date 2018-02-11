package by.dziuba.subscription.command.impl.cart;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class DeleteFromCartCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        //TODO error when cart is empty and delete request sent?
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            Periodical periodical = periodicalService.getByPeriodicalId(Integer.parseInt(requestContent.getRequestParameter("id")));
            Set<Periodical> periodicals = (Set<Periodical>)requestContent.getSessionAttribute("cart_products");
            periodicals.remove(periodical);
            BigDecimal totalPrice = calculateTotalPrice(requestContent, periodical);

            commandResult.putSessionAttribute("totalPrice", totalPrice);
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
        quantities.remove(periodical.getId());
        return totalPrice;
    }
}
