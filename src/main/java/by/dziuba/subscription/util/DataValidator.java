package by.dziuba.subscription.util;

import by.dziuba.subscription.entity.Periodical;
import by.dziuba.subscription.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Validates user, periodical, password, genre/author name using regular expressions.
 * @see User
 * @see Periodical
 */
public class DataValidator {
    private static final Logger LOGGER = LogManager.getLogger(DataValidator.class);

    private static final String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9-_.]{2,18}$";
    private static final String NAME_REGEX = "^[A-Z\\u0400-\\u04ff][a-z\\u0400-\\u04ff.'-]{2,18}$";
    private static final String CITY_REGEX = "^[a-zA-Z\\u0400-\\u04ff]+(?:[\\s-][a-zA-Z\\u0400-\\u04ff]+)*$";
    private static final String PASSWORD_REGEX = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String POSTAL_REGEX = "^[2][0-9]{5}$";
    private static final String ADDRESS_REGEX = "^[a-zA-Z\\u0400-\\u04ff]+(?:[\\s-.,/\\\\]+[a-zA-Z\\u0400-\\u04ff0-9]+)*$";
    private static final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    private static final String GENRE_REGEX = "^[А-ЯЁA-Z][А-ЯЁA-Zа-яёa-z\\s]{3,45}$";
    private static final String AUTHOR_REGEX = "^[А-ЯЁA-Z][А-ЯЁA-Zа-яёa-z\\s]{3,99}$";
    private static final String TITLE_REGEX = "^[А-ЯЁA-Z][A-Za-z\\u0400-\\u04ff\\s]{3,255}$";
    private static final String PRICE_REGEX = "^\\d+(([.,])\\d{1,2})?$";
    private static final String DESCRIPTION_REGEX = "^[А-ЯЁA-Z][A-Za-z\\u0400-\\u04ff\\s\\p{Punct}\\p{Digit}«»—]{3,63000}$";
    private static final String PERIODICITY_REGEX = "[0-9]{1,2}";
    private static final int MAX_PERIODICITY = 30;
    private static final int MIN_PERIODICITY = 1;

    private DataValidator() {
    }

    public static boolean validateUser(User user) {
        return user.getLogin().matches(LOGIN_REGEX) &&
               validatePassword(user.getPassword()) &&
               user.getAddress().matches(ADDRESS_REGEX) &&
               user.getCity().matches(CITY_REGEX) &&
               user.getEmail().matches(EMAIL_REGEX) &&
               user.getFirstName().matches(NAME_REGEX) &&
               user.getLastName().matches(NAME_REGEX) &&
               user.getPostalIndex().matches(POSTAL_REGEX) &&
               validateImage(user.getPhoto());
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validatePeriodical(Periodical periodical) {
        return periodical.getTitle().matches(TITLE_REGEX) &&
               periodical.getPrice().toString().matches(PRICE_REGEX) &&
               periodical.getDescription().matches(DESCRIPTION_REGEX) &&
               periodical.getPeriodicity() >= MIN_PERIODICITY &&
               periodical.getPeriodicity() <= MAX_PERIODICITY &&
               String.valueOf(periodical.getPeriodicity()).matches(PERIODICITY_REGEX);
    }

    public static boolean validateAuthorName(String authorName) {
        return authorName != null && authorName.matches(AUTHOR_REGEX);
    }

    public static boolean validateGenreName(String genreName) {
        return genreName != null && genreName.matches(GENRE_REGEX);
    }

    private static boolean validateImage(String imageURL) {
        try {
            if (imageURL != null && !imageURL.endsWith("svg") && !imageURL.isEmpty()) {
                ImageIO.setUseCache(false);
                BufferedImage image = ImageIO.read(new URL(imageURL));
                return image.getHeight() <= 300 && image.getWidth() <= 300;
            }
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            LOGGER.warn(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
