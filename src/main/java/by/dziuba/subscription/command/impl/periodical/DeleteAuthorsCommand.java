package by.dziuba.subscription.command.impl.periodical;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.RequestContent;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.AuthorService;
import by.dziuba.subscription.service.PeriodicalService;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.util.List;

import static by.dziuba.subscription.command.CommandResult.RoutingType.REDIRECT;

public class DeleteAuthorsCommand implements Command {
    private static final AuthorService authorService = new AuthorServiceImpl();
    private static final PeriodicalService periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException {
        try {
            CommandResult commandResult = new CommandResult(REDIRECT, requestContent.getReferer());
            for (String authorIdParam : requestContent.getRequestParameterValues("authors")) {
                int authorId = Integer.parseInt(authorIdParam);
                List<Periodical> periodicals = periodicalService.getByAuthorId(authorId);
                if (!periodicals.isEmpty()) {
                    commandResult.setErrorCode(400);
                    commandResult.setErrorMessage("Cannot delete " + authorService
                            .getById(authorId).getFullName() + ": it's being referenced by " + periodicals.get(0).getTitle());
                    break;
                } else {
                    authorService.deleteById(authorId);
                }
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
