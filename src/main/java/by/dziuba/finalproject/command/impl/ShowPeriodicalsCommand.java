package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.entity.*;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicityServiceImpl;

import java.util.List;
import java.util.Map;

public class ShowPeriodicalsCommand implements Command {
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();
    private static final GenreServiceImpl genreService = new GenreServiceImpl();
    private static final AuthorServiceImpl authorService = new AuthorServiceImpl();
    private static final PeriodicityServiceImpl periodicityService = new PeriodicityServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        CommandResult commandResult = new CommandResult();
        try {
            List<Periodical> periodicals = periodicalService.getAll();
            Map<Integer, PeriodicalType> periodicalTypes = periodicalService.getAllTypes();
            Map<Integer, List<Genre>> genres = genreService.getAll();
            Map<Integer, Author> authors = authorService.getAll();
            Map<Integer, Periodicity> periodicities = periodicityService.getAll();
            commandResult.putRequestAttribute("genres", genres);
            commandResult.putRequestAttribute("authors", authors);
            commandResult.putRequestAttribute("periodicities", periodicities);
            commandResult.putRequestAttribute("periodicals", periodicals);
            commandResult.putRequestAttribute("periodicalTypes", periodicalTypes);
            commandResult.setPage(JspResourceManager.PERIODICALS_PAGE);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
