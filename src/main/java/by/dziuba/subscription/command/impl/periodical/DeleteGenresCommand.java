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
import java.util.List;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class DeleteGenresCommand implements Command {
    private static final GenreService genreService = new GenreServiceImpl();
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            for (String genreName : requestContent.getRequestParameterValues(ParameterConstant.GENRES)) {
                List<Integer> periodicalIds = genreService.getPeriodicalsByGenreName(genreName);
                if (!periodicalIds.isEmpty()) {
                    String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
                    String msg = MessageManager.getMessage(MessageConstant.CANT_DELETE_ITEM, locale);
                    return new CommandResult(HttpServletResponse.SC_BAD_REQUEST, String
                            .format(msg, genreName, periodicalService.getByPeriodicalId(periodicalIds.get(0)).getTitle()));
                } else {
                    genreService.deleteGenre(genreName);
                }
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
