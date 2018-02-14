package by.dziuba.subscription.filter;

import by.dziuba.subscription.command.CommandProvider;
import by.dziuba.subscription.command.CommandType;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.constant.ParameterConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines granted commands for guests and restricts access to other commands
 * for unauthorized users.
 *
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD})
public class AuthenticationFilter implements Filter {
    private Set<String> grantedCommands = new HashSet<>();

    /**
     * Adds granted commands for guests from EnumSet.
     * @see CommandType
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        EnumSet<CommandType> guestCommands = EnumSet.range(CommandType.LOGIN, CommandType.SEARCH_PERIODICALS);
        guestCommands.forEach(commandType -> grantedCommands.add(commandType.name()));
    }

    /**
     * Filters request and response if user is logged in
     * or if granted commands contain requested command.
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession currentSession = request.getSession(false);

        boolean userLoggedIn = currentSession != null && currentSession.getAttribute(ParameterConstant.USER) != null;

        if (request.getParameter(ParameterConstant.COMMAND) == null) {
            response.sendRedirect(request.getContextPath() + JspPath.INDEX_PAGE);
            return;
        }
        String command = CommandProvider.convertCommandType(request.getParameter(ParameterConstant.COMMAND));

        if (grantedCommands.contains(command) || userLoggedIn) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + JspPath.LOGIN_PAGE_COMMAND);
        }
    }

    @Override
    public void destroy() {
    }
}
