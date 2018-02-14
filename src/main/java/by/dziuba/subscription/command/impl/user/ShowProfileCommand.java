package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.Subscription;
import by.dziuba.subscription.entity.User;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.SubscriptionService;
import by.dziuba.subscription.service.UserService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.service.impl.SubscriptionServiceImpl;
import by.dziuba.subscription.service.impl.UserServiceImpl;
import by.dziuba.subscription.util.MessageManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowProfileCommand implements Command {
    private static final SubscriptionService subscriptionService = new SubscriptionServiceImpl();
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(JspPath.PROFILE_PAGE);
            int userId;
            if (requestContent.getRequestParameter(ParameterConstant.USER_ID) != null) {
                userId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.USER_ID));
            } else {
                userId = ((User) requestContent.getSessionAttribute(ParameterConstant.USER)).getId();
            }
            User user = userService.getUserById(userId);
            List<Subscription> userSubscriptions = subscriptionService.getSubscriptionByUserId(userId);
            Map<Integer, Periodical> userPeriodicals = new HashMap<>();
            for (Subscription subscription : userSubscriptions) {
                userPeriodicals.put(subscription.getPeriodicalId(), periodicalService.getByPeriodicalId(subscription.getPeriodicalId()));
            }
            commandResult.putRequestAttribute(ParameterConstant.USER, user);
            commandResult.putRequestAttribute(ParameterConstant.SUBSCRIPTIONS, userSubscriptions);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICALS, userPeriodicals);
            commandResult.putRequestAttribute(ParameterConstant.STATUSES, defineSubscriptionStatus(userSubscriptions, requestContent));
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private Map<Integer, String> defineSubscriptionStatus(List<Subscription> subscriptions, RequestContent requestContent) {
        Map<Integer, String> statuses = new HashMap<>();
        for (Subscription subscription : subscriptions) {
            LocalDate today = LocalDate.now();
            LocalDate startDate = subscription.getStartDate().toLocalDate();
            LocalDate endDate = subscription.getEndDate().toLocalDate();
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            if (startDate.isAfter(today)) {
                String notYetStatus = MessageManager.getMessage(MessageConstant.STATUS_NOTYET, locale);
                statuses.put(subscription.getPeriodicalId(), notYetStatus);
            } else if ((startDate.isEqual(today) || startDate.isBefore(today)) && endDate.isAfter(today)) {
                String activeStatus = MessageManager.getMessage(MessageConstant.STATUS_ACTIVE, locale);
                statuses.put(subscription.getPeriodicalId(), activeStatus);
            } else {
                String expiredStatus = MessageManager.getMessage(MessageConstant.STATUS_EXPIRED, locale);
                statuses.put(subscription.getPeriodicalId(), expiredStatus);
            }
        }
        return statuses;
    }
}
