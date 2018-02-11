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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class AddToCartCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final int INITIAL_MONTHS_AMOUNT = 1;

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            Periodical periodical = periodicalService.getByPeriodicalId(Integer.parseInt(requestContent.getRequestParameter("id")));
            Map<Integer, Integer> quantities = defineQuantities(requestContent, periodical.getId());

            Set<Periodical> cartPeriodicals = new LinkedHashSet<>();
            if (requestContent.getSessionAttribute("cart_products") != null) {
                ((Set<Periodical>)requestContent.getSessionAttribute("cart_products")).add(periodical);
            } else {
                cartPeriodicals.add(periodical);
                commandResult.putSessionAttribute("cart_products", cartPeriodicals);
            }

            commandResult.putSessionAttribute("totalPrice", calculateTotalPrice(requestContent, periodical));
            commandResult.putSessionAttribute("quantities", quantities);
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

    private Map<Integer, Integer> defineQuantities(RequestContent requestContent, int periodicalId) {
        Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute("quantities");
        if (quantities == null) {
            quantities = new HashMap<>();
        }

        if (quantities.computeIfPresent(periodicalId, (k, v) -> v + 1) == null) {
            quantities.put(periodicalId, INITIAL_MONTHS_AMOUNT);
        }
        return quantities;
    }
}
