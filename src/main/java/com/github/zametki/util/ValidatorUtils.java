package com.github.zametki.util;

import com.github.zametki.model.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Set of validation methods for login, email, first and last name etc..
 */
public class ValidatorUtils {
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)", Pattern.CASE_INSENSITIVE);

    private static final Set<Character> VALID_LOGIN_CHARS = new HashSet<>() {{
        addAll(Arrays.asList('-', '_', '.', ' '));
    }};

    /**
     * Checks if the provided string is valid user password.
     *
     * @param password - string to check, may be null.
     * @return true if {@code password} is a valid user password. Otherwise returns false.
     */
    public static boolean isValidPassword(@Nullable String password) {
        return TextUtils.isLengthInRange(password, User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH);
    }

    /**
     * Checks if the provided string is valid email address.
     *
     * @param email - string to check, may be null.
     * @return true if {@code email} is a valid email address. Otherwise returns false.
     */
    public static boolean isValidEmail(@Nullable String email) {
        return TextUtils.isLengthInRange(email, User.EMAIL_MIN_LENGTH, User.EMAIL_MAX_LENGTH) && EMAIL_PATTERN.matcher(email).matches();
    }


    /**
     * Checks if the provided string is valid first or last name.
     *
     * @param name - string to check, may be null.
     * @return true if {@code name} is a valid first or last name. Otherwise returns false.
     */
    public static boolean isValidFirstOrLastName(@Nullable String name) {
        if (!TextUtils.isLengthInRange(name, User.FIRST_LAST_NAME_MIN_LENGTH, User.FIRST_LAST_NAME_MAX_LENGTH)) {
            return false;
        }
        char[] chars = name.toCharArray();
        if (!Character.isLetter(chars[0]) || !Character.isLetter(chars[chars.length - 1])) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !(c == '-' || c == ' ' || c == '.')) {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns true if the String length is in specified range.
     */
    @Contract("null,_,_ -> false")
    public static boolean isLengthInRange(@Nullable String val, int minLength, int maxLength) {
        return val != null && val.length() >= minLength && val.length() <= maxLength;
    }

    /**
     * Checks if the provided string is valid user login.
     *
     * @param login - string to check, may be null.
     * @return true if {@code login} is a valid user login. Otherwise returns false.
     */
    public static boolean isValidLogin(@Nullable String login) {
        if (!isLengthInRange(login, User.LOGIN_MIN_LENGTH, User.LOGIN_MAX_LENGTH)) {
            return false;
        }
        char[] chars = login.toCharArray();
        if (!Character.isLetter(chars[0]) || !Character.isLetterOrDigit(chars[chars.length - 1])) {
            return false;
        }
        for (char c : chars) {
            if (!(Character.isLetterOrDigit(c) || VALID_LOGIN_CHARS.contains(c))) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public static String validatePassword(@Nullable String password1, @Nullable String password2) {
        if (TextUtils.isEmpty(password1) && TextUtils.isEmpty(password2)) {
            return "Пароли пусты!";
        }
        if (password1 == null || !password1.equals(password2)) {
            return "Пароли не совпадают";
        }
        if (password1.length() < User.PASSWORD_MIN_LENGTH || password1.length() > User.PASSWORD_MAX_LENGTH) {
            return password1.length() < User.PASSWORD_MIN_LENGTH ? "Пароль слишком короткий: менее " + User.PASSWORD_MIN_LENGTH + " символов." : "Пароль слишком длиный: более " + User.PASSWORD_MAX_LENGTH + " символов";
        }
        if (!isPrintableAsciiCharacters(password1)) {
            return "Пароль содержит недопустимые символы";
        }
        return null;
    }

    private static boolean isPrintableAsciiCharacters(@NotNull String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c <= 32 || c >= 128) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public static String validateRange(@Nullable String value, int minLen, int maxLen, @NotNull String minMessage, @NotNull String maxMessage) {
        if (value == null || value.length() < minLen) {
            return minMessage;
        }
        if (value.length() > maxLen) {
            return maxMessage;
        }
        return null;
    }

}
