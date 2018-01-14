package by.dziuba.finalproject.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    private String requestURI;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    public RequestContent(HttpServletRequest req) {
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>(req.getParameterMap());
        requestURI = req.getRequestURI();
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
        return requestParameters.get(parameter)[0];
    }

    public Object getSessionAttribute(String attribute) {
        return sessionAttributes.get(attribute);
    }

    public String getRequestURI() {
        return requestURI;
    }

    public Map<String, String[]> getRequestParameters() {
        return Collections.unmodifiableMap(requestParameters);
    }
}
