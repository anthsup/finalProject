package by.dziuba.subscription.command.impl.cart;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

/**
 * Deletes periodical from cart by its id. Sends error if id is invalid.
 * Recalculates and sets new total price.
 */
public class DeleteFromCartCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            Periodical periodical = periodicalService.getByPeriodicalId(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.PERIODICAL_ID)));
            Set<Periodical> periodicals = (Set<Periodical>)requestContent.getSessionAttribute(ParameterConstant.CART_PRODUCTS);

            if (periodicals.contains(periodical)) {
                periodicals.remove(periodical);
            } else {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST,
                        MessageManager.getMessage(MessageConstant.CART_PRODUCT_NOT_FOUND, locale));
            }

            BigDecimal totalPrice = calculateTotalPrice(requestContent, periodical);
            commandResult.putSessionAttribute(ParameterConstant.TOTAL_PRICE, totalPrice);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private BigDecimal calculateTotalPrice(RequestContent requestContent, Periodical periodical) {
        BigDecimal totalPrice = (BigDecimal)requestContent.getSessionAttribute(ParameterConstant.TOTAL_PRICE);
        Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute(ParameterConstant.QUANTITIES);
        int quantity = quantities.get(periodical.getId());
        totalPrice = totalPrice.subtract(periodical.getPrice().multiply(BigDecimal.valueOf(quantity)));
        quantities.remove(periodical.getId());
        return totalPrice;
    }
}
