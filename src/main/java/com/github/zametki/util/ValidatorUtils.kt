package com.github.zametki.util

import com.github.zametki.model.User
import org.jetbrains.annotations.Contract
import java.util.*
import java.util.regex.Pattern

/**
 * Set of validation methods for login, email, first and last name etc..
 */
object ValidatorUtils {

    private val HEX_COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")

    private val EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)", Pattern.CASE_INSENSITIVE)

    private val VALID_LOGIN_CHARS = object : HashSet<Char>() {
        init {
            addAll(Arrays.asList('-', '_', '.', ' '))
        }
    }

    /**
     * Checks if the provided string is valid user password.
     *
     * @param password - string to check, may be null.
     * @return true if `password` is a valid user password. Otherwise returns false.
     */
    fun isValidPassword(password: String?) = TextUtils.isLengthInRange(password, User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH)

    /**
     * Checks if the provided string is valid email address.
     *
     * @param email - string to check, may be null.
     * @return true if `email` is a valid email address. Otherwise returns false.
     */
    fun isValidEmail(email: String?) = TextUtils.isLengthInRange(email, User.EMAIL_MIN_LENGTH, User.EMAIL_MAX_LENGTH) && EMAIL_PATTERN.matcher(email!!).matches()


    /**
     * Checks if the provided string is valid first or last name.
     *
     * @param name - string to check, may be null.
     * @return true if `name` is a valid first or last name. Otherwise returns false.
     */
    fun isValidFirstOrLastName(name: String?): Boolean {
        if (!TextUtils.isLengthInRange(name, User.FIRST_LAST_NAME_MIN_LENGTH, User.FIRST_LAST_NAME_MAX_LENGTH)) {
            return false
        }
        val chars = name!!.toCharArray()
        if (!Character.isLetter(chars[0]) || !Character.isLetter(chars[chars.size - 1])) {
            return false
        }
        return name.toCharArray().none { !Character.isLetter(it) && !(it == '-' || it == ' ' || it == '.') }
    }


    /**
     * Returns true if the String length is in specified range.
     */
    @Contract("null,_,_ -> false")
    fun isLengthInRange(text: String?, minLength: Int, maxLength: Int) = text != null && text.length >= minLength && text.length <= maxLength

    /**
     * Checks if the provided string is valid user login.
     *
     * @param login - string to check, may be null.
     * @return true if `login` is a valid user login. Otherwise returns false.
     */
    fun isValidLogin(login: String?): Boolean {
        if (!isLengthInRange(login, User.LOGIN_MIN_LENGTH, User.LOGIN_MAX_LENGTH)) {
            return false
        }
        val chars = login!!.toCharArray()
        if (!Character.isLetter(chars[0]) || !Character.isLetterOrDigit(chars[chars.size - 1])) {
            return false
        }
        return chars.any { Character.isLetterOrDigit(it) || VALID_LOGIN_CHARS.contains(it) }
    }

    fun validatePassword(password1: String?, password2: String?): String? {
        if (TextUtils.isEmpty(password1) && TextUtils.isEmpty(password2)) {
            return "Пароли пусты!"
        }
        if (password1 == null || password1 != password2) {
            return "Пароли не совпадают"
        }
        if (password1.length < User.PASSWORD_MIN_LENGTH || password1.length > User.PASSWORD_MAX_LENGTH) {
            return if (password1.length < User.PASSWORD_MIN_LENGTH) "Пароль слишком короткий: менее " + User.PASSWORD_MIN_LENGTH + " символов." else "Пароль слишком длиный: более " + User.PASSWORD_MAX_LENGTH + " символов"
        }
        return if (!isPrintableAsciiCharacters(password1)) "Пароль содержит недопустимые символы" else null
    }

    private fun isPrintableAsciiCharacters(text: String): Boolean {
        return (0 until text.length)
                .map { text[it] }
                .none { it.toInt() <= 32 || it.toInt() >= 128 }
    }

    fun validateRange(value: String?, minLen: Int, maxLen: Int, minMessage: String, maxMessage: String): String? {
        if (value == null || value.length < minLen) {
            return minMessage
        }
        return if (value.length > maxLen) maxMessage else null
    }

}
