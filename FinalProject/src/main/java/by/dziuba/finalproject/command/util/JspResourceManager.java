package by.dziuba.finalproject.command.util;

import java.util.ResourceBundle;

public final class JspResourceManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");

    public static final String INDEX_PAGE = "jsp.index";
    public static final String ABOUT_PAGE = "jsp.about";
    public static final String LOGIN_PAGE = "jsp.login";

    public static final String LOGIN_PAGE_COMMAND = "page.login";

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
