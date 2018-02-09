package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.Subscription;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.service.impl.SubscriptionServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowProfileCommand implements Command {
    private static final SubscriptionServiceImpl subscriptionService = new SubscriptionServiceImpl();
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            int userId;
            if (requestContent.getRequestParameter("id") != null) {
                userId = Integer.parseInt(requestContent.getRequestParameter("id"));
            } else {
                userId = ((User) requestContent.getSessionAttribute("user")).getId();
            }
            User user = userService.getUserById(userId);
            List<Subscription> userSubscriptions = subscriptionService.getSubscriptionByUserId(userId);
            Map<Integer, Periodical> userPeriodicals = new HashMap<>();
            for (Subscription subscription : userSubscriptions) {
                userPeriodicals.put(subscription.getPeriodicalId(), periodicalService.getByPeriodicalId(subscription.getPeriodicalId()));
            }
            commandResult.putRequestAttribute("user", user);
            commandResult.putRequestAttribute("subscriptions", userSubscriptions);
            commandResult.putRequestAttribute("periodicals", userPeriodicals);
            commandResult.putRequestAttribute("statuses", defineSubscriptionStatus(userSubscriptions));
            commandResult.setPage(JspResourceManager.PROFILE_PAGE);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private Map<Integer, String> defineSubscriptionStatus(List<Subscription> subscriptions) {
        Map<Integer, String> statuses = new HashMap<>();
        for (Subscription subscription : subscriptions) {
            LocalDate today = LocalDate.now();
            LocalDate startDate = subscription.getStartDate().toLocalDate();
            LocalDate endDate = subscription.getEndDate().toLocalDate();
            if (startDate.isAfter(today)) {
                statuses.put(subscription.getPeriodicalId(), "Isn't active yet.");
            } else if ((startDate.isEqual(today) || startDate.isBefore(today)) && endDate.isAfter(today)) {
                statuses.put(subscription.getPeriodicalId(), "Active.");
            } else {
                statuses.put(subscription.getPeriodicalId(), "Expired.");
            }
        }
        return statuses;
    }
}
