package by.dziuba.subscription.filter;

import by.dziuba.subscription.command.CommandProvider;
import by.dziuba.subscription.command.CommandType;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.entity.User;

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
 * Restricts access to some commands for non-admin users.
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD})
public class AuthorizationFilter implements Filter {
    private Set<String> adminCommands = new HashSet<>();

    /**
     * Adds admin commands as string names from defined EnumSet.
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        EnumSet<CommandType> adminCommandTypes = EnumSet.range(CommandType.BAN_USER, CommandType.ADD_PERIODICAL);
        adminCommandTypes.forEach(commandType -> adminCommands.add(commandType.name()));
    }

    /**
     * Filters request and response if user is admin
     * or if admin commands don't contain requested command.
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
        HttpSession currentSession = request.getSession(false);
        if (currentSession == null || currentSession.getAttribute(ParameterConstant.USER) == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            authorize(servletRequest, servletResponse, filterChain);
        }
    }

    private void authorize(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User currentUser = (User) request.getSession().getAttribute(ParameterConstant.USER);
        boolean isAdmin = currentUser.isAdmin();

        if (request.getParameter(ParameterConstant.COMMAND) == null) {
            response.sendRedirect(request.getContextPath() + JspPath.INDEX_PAGE);
            return;
        }
        String command = CommandProvider.convertCommandType(request.getParameter(ParameterConstant.COMMAND));
        if (isAdmin || !adminCommands.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
    }
}
