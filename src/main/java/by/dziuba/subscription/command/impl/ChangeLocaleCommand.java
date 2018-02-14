package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class ChangeLocaleCommand implements Command {
    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
        String selectedLocale = requestContent.getRequestParameter(ParameterConstant.LOCALE);

        if (selectedLocale == null ||
                (!selectedLocale.equalsIgnoreCase(ParameterConstant.EN_LOCALE) && !selectedLocale.equalsIgnoreCase(ParameterConstant.RU_LOCALE))) {
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            String msg = MessageManager.getMessage(MessageConstant.INVALID_LOCALE, locale);
            return new CommandResult(HttpServletResponse.SC_BAD_REQUEST, msg);
        }

        commandResult.putSessionAttribute(ParameterConstant.LOCALE, selectedLocale);
        return commandResult;
    }
}
