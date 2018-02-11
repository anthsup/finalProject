package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.Author;
import by.dziuba.subscription.entity.Genre;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.PeriodicalType;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.GenreService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.PeriodicalTypeService;
import by.dziuba.subscription.service.exception.ServiceException;
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
        CommandResult commandResult = new CommandResult(JspResourceManager.PERIODICALS_PAGE);
        try {
            String pageNumber = Optional.ofNullable(requestContent.getRequestParameter("page")).orElse("1");
            String periodicalsPerPage = Optional.ofNullable(requestContent.getRequestParameter("periodicalsPerPage")).orElse("8");

            List<Periodical> periodicals = periodicalService.getAll(Integer.parseInt(pageNumber), Integer.parseInt(periodicalsPerPage));
            Map<Integer, PeriodicalType> periodicalTypes = periodicalTypeService.getAll().stream()
                    .collect(Collectors.toMap(PeriodicalType::getId, Function.identity()));
            Map<Integer, List<Genre>> genres = genreService.getAllPeriodicalGenres();
            Map<Integer, Author> authors = authorService.getAll().stream()
                    .collect(Collectors.toMap(Author::getId, Function.identity()));

            commandResult.putRequestAttribute("genres", genres);
            commandResult.putRequestAttribute("authors", authors);
            commandResult.putRequestAttribute("periodicals", periodicals);
            commandResult.putRequestAttribute("periodicalTypes", periodicalTypes);
            commandResult.putRequestAttribute("periodicalsNumber", periodicalService.getPeriodicalsNumber());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
