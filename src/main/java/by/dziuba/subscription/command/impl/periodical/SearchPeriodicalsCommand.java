package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.BadRequestException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchPeriodicalsCommand implements Command {
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();
    private static final PeriodicalTypeService periodicalTypeService = new PeriodicalTypeServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        CommandResult commandResult = new CommandResult(JspResourceManager.PERIODICALS_PAGE);
        try {
            List<Periodical> foundPeriodicals = findPeriodicals(requestContent);

            Map<Integer, PeriodicalType> periodicalTypes = periodicalTypeService.getAll().stream()
                    .collect(Collectors.toMap(PeriodicalType::getId, Function.identity()));
            Map<Integer, List<Genre>> genres = genreService.getAllPeriodicalGenres();
            Map<Integer, Author> authors = authorService.getAll().stream()
                    .collect(Collectors.toMap(Author::getId, Function.identity()));

            commandResult.putRequestAttribute("genres", genres);
            commandResult.putRequestAttribute("authors", authors);
            commandResult.putRequestAttribute("periodicals", foundPeriodicals);
            commandResult.putRequestAttribute("periodicalTypes", periodicalTypes);
            commandResult.putRequestAttribute("periodicalsNumber", foundPeriodicals.size());
            commandResult.putRequestAttribute("searchCommand", requestContent.getRequestURL());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

    private List<Periodical> findPeriodicals(RequestContent requestContent) throws ServiceException, BadRequestException {
        int pageNumber = Integer.parseInt(Optional.ofNullable(requestContent.getRequestParameter("page")).orElse("1"));
        int periodicalsPerPage = Integer.parseInt(Optional.ofNullable(requestContent
                .getRequestParameter("periodicalsPerPage")).orElse("8"));

        if (requestContent.getRequestParameter("periodicalType") != null) {
            return periodicalService.getByPeriodicalType(Integer.parseInt(requestContent
                    .getRequestParameter("periodicalType")), pageNumber, periodicalsPerPage);
        } else if (requestContent.getRequestParameter("periodicity") != null) {
            return periodicalService.getByPeriodicity(Integer.parseInt(requestContent
                    .getRequestParameter("periodicity")), pageNumber, periodicalsPerPage);
        } else if (requestContent.getRequestParameter("genre") != null) {
            List<Periodical> foundPeriodicals = new ArrayList<>();
            for (int periodicalId : genreService.getPeriodicalsByGenreName(requestContent.getRequestParameter("genre"))) {
                foundPeriodicals.add(periodicalService.getByPeriodicalId(periodicalId));
            }
            return foundPeriodicals;
        } else if (requestContent.getRequestParameter("author") != null) {
            return periodicalService.getByAuthorId(Integer.parseInt(requestContent
                    .getRequestParameter("author")), pageNumber, periodicalsPerPage);
        }
        throw new BadRequestException("Nothing has been found.");
    }
}
