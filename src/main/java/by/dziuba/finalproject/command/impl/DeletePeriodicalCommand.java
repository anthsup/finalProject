package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

public class DeletePeriodicalCommand implements Command {
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();
    private static final GenreServiceImpl genreService = new GenreServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
            int periodicalId = Integer.parseInt(requestContent.getRequestParameter("id"));
            genreService.deleteById(periodicalId);
            periodicalService.deleteById(periodicalId);
            commandResult.setRedirected(true);
            commandResult.setPage(requestContent.getReferer());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
