package by.dziuba.finalproject.util.validator;

public final class AccountValidator {
    private static final String LOGIN_REGEXP = "^[A-Za-z0-9_]{5,15}$";
    private static final String EMAIL_REGEXP = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private static final String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";

    private AccountValidator() {
    }

    public static boolean validateLogin(final String login) {
        return login.matches(LOGIN_REGEXP);
    }

    public static boolean validateEmail(final String email) {
        return email.matches(EMAIL_REGEXP);
    }

    public static boolean validatePassword(final String password) {
        return password.matches(PASSWORD_REGEXP);
    }

    public static boolean checkPasswordsMatch(final String password, final String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    public static boolean validateAuthorization(final String loginOrEmail, final String password) {
        return ((loginOrEmail.matches(LOGIN_REGEXP) || loginOrEmail.matches(EMAIL_REGEXP)) && validatePassword(password));
    }
}