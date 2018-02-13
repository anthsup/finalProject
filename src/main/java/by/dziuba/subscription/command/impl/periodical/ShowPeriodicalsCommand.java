package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.exception.CommandException;
import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.PeriodicalTypeService;
import by.dziuba.subscription.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.GenreServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalTypeServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowPeriodicalsCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final PeriodicalTypeService periodicalTypeService = new PeriodicalTypeServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        CommandResult commandResult = new CommandResult(JspPath.PERIODICALS_PAGE);
        try {
            String pageNumber = Optional.ofNullable(requestContent
                    .getRequestParameter(ParameterConstant.PAGE)).orElse(SearchPeriodicalsCommand.DEFAULT_PAGE_NUMBER);
            String periodicalsPerPage = Optional.ofNullable(requestContent
                    .getRequestParameter(ParameterConstant.PERIODICALS_PER_PAGE)).orElse(SearchPeriodicalsCommand.DEFAULT_PERIODICALS_PER_PAGE);

            List<Periodical> periodicals = periodicalService.getAll(Integer.parseInt(pageNumber), Integer.parseInt(periodicalsPerPage));
            Map<Integer, PeriodicalType> periodicalTypes = periodicalTypeService.getAll().stream()
                    .collect(Collectors.toMap(PeriodicalType::getId, Function.identity()));
            Map<Integer, List<Genre>> genres = genreService.getAllPeriodicalGenres();
            Map<Integer, Author> authors = authorService.getAll().stream()
                    .collect(Collectors.toMap(Author::getId, Function.identity()));

            commandResult.putRequestAttribute(ParameterConstant.GENRES, genres);
            commandResult.putRequestAttribute(ParameterConstant.AUTHORS, authors);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICALS, periodicals);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICAL_TYPES, periodicalTypes);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICAL_NUMBER, periodicalService.getPeriodicalsNumber());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
