package by.dziuba.subscription.command.impl;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.exception.BadRequestException;
import by.dziuba.subscription.command.exception.CommandException;
import by.dziuba.subscription.command.util.CommandResult;
import by.dziuba.subscription.command.util.RequestContent;
import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.service.exception.ServiceException;
import by.dziuba.subscription.service.impl.AuthorServiceImpl;
import by.dziuba.subscription.service.impl.PeriodicalServiceImpl;

import java.util.List;

public class DeleteAuthorsCommand implements Command {
    private static final AuthorServiceImpl authorService = new AuthorServiceImpl();
    private static final PeriodicalServiceImpl periodicalService = new PeriodicalServiceImpl();

    @Override
    public CommandResult execute(RequestContent requestContent) throws CommandException, BadRequestException {
        try {
            CommandResult commandResult = new CommandResult();
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
            commandResult.setPage(requestContent.getReferer());
            commandResult.setRedirected(true);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
