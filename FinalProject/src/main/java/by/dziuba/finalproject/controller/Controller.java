package by.dziuba.finalproject.controller;

import by.dziuba.finalproject.command.Command;
import by.dziuba.finalproject.command.CommandResult;
import by.dziuba.finalproject.command.CommandProvider;
import by.dziuba.finalproject.command.RequestContent;
import by.dziuba.finalproject.command.exception.BadRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CommandProvider client = new CommandProvider();
            RequestContent requestContent = new RequestContent(req);
            Command command = client.defineCommand(requestContent);
            CommandResult executionResult = command.execute(requestContent);
            executionResult.updateRequest(req);

            if (executionResult.isRedirected() && executionResult.getErrorCode() == null) {
                resp.sendRedirect(executionResult.getPage());
            } else if (executionResult.getErrorCode() == null) {
                getServletContext().getRequestDispatcher(executionResult.getPage()).forward(req, resp);
            } else {
                resp.sendError(executionResult.getErrorCode(),executionResult.getErrorMessage());
            }
//        } catch (CommandException e){
//            LOGGER.warn(e.getMessage());
//            throw new ServletException(e);
        } catch (BadRequestException e) {
            LOGGER.warn(e.getMessage());
            resp.sendError(400, e.getMessage());
        }
    }
}
