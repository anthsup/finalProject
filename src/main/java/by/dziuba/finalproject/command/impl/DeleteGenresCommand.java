package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.util.List;

public class DeleteGenresCommand implements Command {
    private static final GenreServiceImpl genreService = new GenreServiceImpl();
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
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
            commandResult.setPage(requestContent.getReferer());
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
