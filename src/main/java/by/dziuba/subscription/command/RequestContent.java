package by.dziuba.subscription.command;

import by.dziuba.subscription.constant.JspPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Proxy class which transfers request data from users to business logic.
 */
public class RequestContent {
    private String requestURL;
    private String referer;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    public RequestContent(HttpServletRequest req) {
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>(req.getParameterMap());
        requestURL = req.getRequestURL().append("?").append(req.getQueryString() == null ? "" : req.getQueryString()).toString();
        if (req.getHeader("Referer") != null) {
            referer = req.getHeader("Referer").equals("http://localhost:8080/controller") ? JspPath.INDEX_PAGE : req.getHeader("Referer");
        } else {
            referer = null;
        }
        HttpSession currentSession = req.getSession(false);
        if (currentSession != null) {
            Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()) {
                String attributeName = sessionAttributeNames.nextElement();
                sessionAttributes.put(attributeName, currentSession.getAttribute(attributeName));
            }
        }
    }

    public String[] getRequestParameterValues(String parameter) {
        return requestParameters.get(parameter);
    }

    public String getRequestParameter(String parameter) {
        return requestParameters.get(parameter) == null ? null : requestParameters.get(parameter)[0];
    }

    public Object getSessionAttribute(String attribute) {
        return sessionAttributes.get(attribute);
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getReferer() {
        return referer;
    }
}
