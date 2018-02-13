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
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "BanFilter", urlPatterns = {"/controller"}, dispatcherTypes = {DispatcherType.REQUEST,
        DispatcherType.FORWARD})
public class BanFilter  implements Filter {
    private Set<String> grantedCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        grantedCommands.add(CommandType.LOGOUT.name());
        grantedCommands.add(CommandType.PROFILE.name());
        grantedCommands.add(CommandType.CHANGE_LOCALE.name());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession currentSession = request.getSession(false);
        User currentUser = (User) currentSession.getAttribute(ParameterConstant.USER);
        String bannedURL = JspPath.BAN_PAGE_COMMAND;
        String requestedURL = request.getRequestURI() + "?" + request.getQueryString();
        boolean banPageRequested = bannedURL.equalsIgnoreCase(requestedURL);

        if (currentUser != null && currentUser.isBanned() && !grantedCommands
                .contains(CommandProvider.convertCommandType(request.getParameter(ParameterConstant.COMMAND))) && !banPageRequested) {
            response.sendRedirect(JspPath.BAN_PAGE_COMMAND);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
