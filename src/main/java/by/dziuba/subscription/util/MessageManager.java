package by.dziuba.subscription.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static ResourceBundle ruBundle = ResourceBundle.getBundle("Contents", new Locale("ru", "RU"));
    private static ResourceBundle enBundle = ResourceBundle.getBundle("Contents", new Locale("en", "US"));

    public static String getMessage(String key, String locale) {
        if ("en_US".equalsIgnoreCase(locale)) {
            return enBundle.getString(key);
        }
        return ruBundle.getString(key);
    }
}
