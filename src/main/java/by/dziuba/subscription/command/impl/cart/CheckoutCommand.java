package by.dziuba.subscription.command.impl.cart;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.Subscription;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.SubscriptionService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.SubscriptionServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class CheckoutCommand implements Command {
    private static final SubscriptionService subscriptionService = new SubscriptionServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            List<Subscription> subscriptions = createSubscriptionList(requestContent);
            subscriptionService.addSubscription(subscriptions);
            if (requestContent.getRequestParameter("credit").equals("true")) {
                User user = (User) requestContent.getSessionAttribute("user");
                user.setLoan(user.getLoan().subtract((BigDecimal) requestContent.getSessionAttribute("totalPrice")));
                userService.updateLoan(user);
            }
            // TODO check for success and set error if somethings's up
            commandResult.putSessionAttribute("cart_products", new LinkedHashSet<Periodical>());
            commandResult.putSessionAttribute("totalPrice", new BigDecimal(0));
            commandResult.putSessionAttribute("quantities", new HashMap<Integer, Integer>());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private List<Subscription> createSubscriptionList(RequestContent requestContent) {
        List<Subscription> subscriptions = new ArrayList<>();
        User user = (User) requestContent.getSessionAttribute("user");
        Subscription subscription;
        for (Periodical periodical : (Set<Periodical>) requestContent.getSessionAttribute("cart_products")) {
            subscription = createSubscription(periodical, requestContent, user);
            subscriptions.add(subscription);
        }
        return subscriptions;
    }

    private Subscription createSubscription(Periodical periodical, RequestContent requestContent, User user) {
        Subscription subscription = new Subscription();
        subscription.setUserId(user.getId());
        subscription.setPeriodicalId(periodical.getId());

        Date startDate = Date.valueOf(LocalDate.now().withDayOfMonth(1).plusMonths(1));
        subscription.setStartDate(startDate);

        Map<Integer, Integer> quantities = (Map<Integer, Integer>) requestContent.getSessionAttribute("quantities");
        int quantity = quantities.get(periodical.getId());
        subscription.setEndDate(Date.valueOf(startDate.toLocalDate().plusMonths(quantity)));
        subscription.setPrice(periodical.getPrice().multiply(BigDecimal.valueOf(quantity)));

        return subscription;
    }
}
