package by.dziuba.subscription.util;

import by.dziuba.subscription.entity.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class DataValidatorTest {
    private String validPassword;
    private String invalidPassword;
    private String validAuthorName;
    private String invalidAuthorName;
    private String validGenreName;
    private String invalidGenreName;
    private User validUser;
    private User invalidUser;

    @BeforeClass
    public void setUp() throws Exception {
        validPassword = "I'mavalidPassw0rd";
        invalidPassword = "Iminvalid";
        validAuthorName = "Valid Author";
        invalidAuthorName = "I'm Invalid Author";
        validGenreName = "Valid Genre";
        invalidGenreName = "Invalid Genre 0";
        validUser = setUserData(false);
        invalidUser = setUserData(true);
    }

    @AfterClass
    public void tearDown() throws Exception {
        validPassword = null;
        validGenreName = null;
        invalidPassword = null;
        validAuthorName = null;
        invalidGenreName = null;
        invalidAuthorName = null;
        validUser = null;
        invalidUser = null;
    }

    @Test
    public void testValidateUser() throws Exception {
        assertTrue(DataValidator.validateUser(validUser) &&
                !DataValidator.validateUser(invalidUser));
    }

    @Test
    public void testValidatePassword() throws Exception {
        assertTrue(DataValidator.validatePassword(validPassword) &&
                !DataValidator.validatePassword(invalidPassword));
    }

    @Test
    public void testValidateAuthorName() throws Exception {
        assertTrue(DataValidator.validateAuthorName(validAuthorName) &&
        !DataValidator.validateAuthorName(invalidAuthorName));
    }

    @Test
    public void testValidateGenreName() throws Exception {
        assertTrue(DataValidator.validateGenreName(validGenreName) &&
        !DataValidator.validateGenreName(invalidGenreName));
    }

    private User setUserData(boolean invalid) {
        User user = new User();
        user.setLogin("epam");
        user.setCity("Minsk");
        user.setAddress("ул. Купревича, 1/2");
        if (invalid) {
            user.setPostalIndex("322228");
        } else {
            user.setPostalIndex("222120");
        }
        user.setPassword("SuperStrongPassword777");
        user.setEmail("epam@epam.com");
        user.setFirstName("Епам");
        user.setLastName("Епамский");
        return user;
    }
}