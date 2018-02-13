package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.exception.BadRequestException;
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
import by.dziuba.subscription.util.MessageManager;

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

    static final String DEFAULT_PAGE_NUMBER = "1";
    static final String DEFAULT_PERIODICALS_PER_PAGE = "8";

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        CommandResult commandResult = new CommandResult(JspPath.PERIODICALS_PAGE);
        try {
            List<Periodical> foundPeriodicals = findPeriodicals(requestContent);

            Map<Integer, PeriodicalType> periodicalTypes = periodicalTypeService.getAll().stream()
                    .collect(Collectors.toMap(PeriodicalType::getId, Function.identity()));
            Map<Integer, List<Genre>> genres = genreService.getAllPeriodicalGenres();
            Map<Integer, Author> authors = authorService.getAll().stream()
                    .collect(Collectors.toMap(Author::getId, Function.identity()));

            commandResult.putRequestAttribute(ParameterConstant.GENRES, genres);
            commandResult.putRequestAttribute(ParameterConstant.AUTHORS, authors);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICALS, foundPeriodicals);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICAL_TYPES, periodicalTypes);
            commandResult.putRequestAttribute(ParameterConstant.PERIODICAL_NUMBER, foundPeriodicals.size());
            commandResult.putRequestAttribute("searchCommand", requestContent.getRequestURL());
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }

    private List<Periodical> findPeriodicals(RequestContent requestContent) throws ServiceException, BadRequestException {
        int pageNumber = Integer.parseInt(Optional.ofNullable(requestContent.getRequestParameter(ParameterConstant.PAGE)).orElse(DEFAULT_PAGE_NUMBER));
        int periodicalsPerPage = Integer.parseInt(Optional.ofNullable(requestContent
                .getRequestParameter(ParameterConstant.PERIODICALS_PER_PAGE)).orElse(DEFAULT_PERIODICALS_PER_PAGE));

        if (requestContent.getRequestParameter(ParameterConstant.PERIODICAL_TYPE) != null) {
            return periodicalService.getByPeriodicalType(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.PERIODICAL_TYPE)), pageNumber, periodicalsPerPage);
        } else if (requestContent.getRequestParameter(ParameterConstant.PERIODICITY) != null) {
            return periodicalService.getByPeriodicity(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.PERIODICITY)), pageNumber, periodicalsPerPage);
        } else if (requestContent.getRequestParameter(ParameterConstant.GENRE) != null) {
            List<Periodical> foundPeriodicals = new ArrayList<>();
            for (int periodicalId : genreService.getPeriodicalsByGenreName(requestContent.getRequestParameter(ParameterConstant.GENRE))) {
                foundPeriodicals.add(periodicalService.getByPeriodicalId(periodicalId));
            }
            return foundPeriodicals;
        } else if (requestContent.getRequestParameter(ParameterConstant.AUTHOR) != null) {
            return periodicalService.getByAuthorId(Integer.parseInt(requestContent
                    .getRequestParameter(ParameterConstant.AUTHOR)), pageNumber, periodicalsPerPage);
        }
        String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
        String msg = MessageManager.getMessage(MessageConstant.NOTHING_FOUND, locale);
        throw new BadRequestException(msg);
    }
}
