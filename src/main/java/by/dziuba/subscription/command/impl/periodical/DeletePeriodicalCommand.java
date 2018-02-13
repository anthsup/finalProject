package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.util.MessageManager;

import javax.servlet.http.HttpServletResponse;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class DeletePeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            int periodicalId = Integer.parseInt(requestContent.getRequestParameter(ParameterConstant.PERIODICAL_ID));
            if (periodicalService.getByPeriodicalId(periodicalId) == null) {
                String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                String msg = MessageManager.getMessage(MessageConstant.PERIODICAL_NOT_FOUND, locale);
                return new CommandResult(HttpServletResponse.SC_BAD_REQUEST, msg);
            }
            genreService.deleteByPeriodicalId(periodicalId);
            periodicalService.deleteById(periodicalId);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
