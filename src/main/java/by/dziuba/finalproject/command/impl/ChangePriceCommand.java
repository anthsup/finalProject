package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

public class ChangePriceCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangePriceCommand.class);
    private PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult();
            Map<Integer, Integer> quantities = (Map<Integer, Integer>)requestContent.getSessionAttribute("quantities");
            Integer id = Integer.parseInt(requestContent.getRequestParameter("id"));
            int quantity = quantities.get(id);
            quantities.put(id, Integer.parseInt(requestContent.getRequestParameter("quantity")));
            BigDecimal price = periodicalService.getById(id).getPrice();
            BigDecimal oldSubtotal = price.multiply(new BigDecimal(quantity));
            BigDecimal newSubtotal = price.multiply(new BigDecimal(quantities.get(id)));
            BigDecimal total = (BigDecimal)requestContent.getSessionAttribute("totalPrice");
            total = total.subtract(oldSubtotal);
            total = total.add(newSubtotal);
            commandResult.putSessionAttribute("totalPrice", total);
            commandResult.setPage(JspResourceManager.CART_PAGE_COMMAND);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
