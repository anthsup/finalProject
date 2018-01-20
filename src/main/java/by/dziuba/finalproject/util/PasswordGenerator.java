package by.dziuba.finalproject.util;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
    private static final int SYMBOL_COUNT = 8;
    private static final int BEGIN = 0;
    private static final int END = 0;
    private static final boolean GENERATE_LETTERS = true;
    private static final boolean GENERATE_NUMBERS = true;
    private static final Random SECURE_RANDOM_INSTANCE = new SecureRandom();
    public static String generate(){
        return RandomStringUtils.random(SYMBOL_COUNT, BEGIN, END, GENERATE_LETTERS, GENERATE_NUMBERS, null, SECURE_RANDOM_INSTANCE);
    }
}
