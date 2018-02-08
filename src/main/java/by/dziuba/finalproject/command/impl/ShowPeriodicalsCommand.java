package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.JspResourceManager;
import by.dziuba.subscription.entity.*;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowPeriodicalsCommand implements Command {
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();
    private static final PeriodicalTypeServiceImpl periodicalTypeService = new PeriodicalTypeServiceImpl();
    private static final GenreServiceImpl genreService = new GenreServiceImpl();
    private static final AuthorServiceImpl authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        CommandResult commandResult = new CommandResult();
        try {
            String pageNumber = Optional.ofNullable(requestContent.getRequestParameter("page")).orElse("1");
            String filmsPerPage = Optional.ofNullable(requestContent.getRequestParameter("periodicalsPerPage")).orElse("8");

            List<Periodical> periodicals = periodicalService.getAll(Integer.parseInt(pageNumber), Integer.parseInt(filmsPerPage));
            Map<Integer, PeriodicalType> periodicalTypes = periodicalTypeService.getAll().stream()
                    .collect(Collectors.toMap(PeriodicalType::getId, Function.identity()));;
            Map<Integer, List<Genre>> genres = genreService.getAllPeriodicalGenres();
            Map<Integer, Author> authors = authorService.getAll().stream()
                    .collect(Collectors.toMap(Author::getId, Function.identity()));

            commandResult.putRequestAttribute("genres", genres);
            commandResult.putRequestAttribute("authors", authors);
            commandResult.putRequestAttribute("periodicals", periodicals);
            commandResult.putRequestAttribute("periodicalTypes", periodicalTypes);
            commandResult.putRequestAttribute("periodicalsNumber", periodicalService.getPeriodicalsNumber());
            commandResult.setPage(JspResourceManager.PERIODICALS_PAGE);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
