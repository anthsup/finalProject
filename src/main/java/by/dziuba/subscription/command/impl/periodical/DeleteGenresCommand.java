package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.util.List;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class DeleteGenresCommand implements Command {
    private static final GenreService genreService = new GenreServiceImpl();
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            for (String genreName : requestContent.getRequestParameterValues("genres")) {
                List<Integer> periodicalIds = genreService.getPeriodicalsByGenreName(genreName);
                if (!periodicalIds.isEmpty()) {
                    commandResult.setErrorCode(400);
                    commandResult.setErrorMessage("Cannot delete " + genreName + ": it's being referenced by " +
                            periodicalService.getByPeriodicalId(periodicalIds.get(0)).getTitle());
                    break;
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
