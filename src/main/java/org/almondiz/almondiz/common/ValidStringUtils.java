package org.almondiz.almondiz.common;

import org.almondiz.almondiz.exception.exception.CNotValidEmailException;

import java.util.regex.Pattern;

public class ValidStringUtils {

    public static final Pattern EMAIL = Pattern.compile(
        "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    public static String getValidEmail(String email) {
        if (!EMAIL.matcher(email).matches()) {
            throw new CNotValidEmailException();
        }
        return email;
    }
}
