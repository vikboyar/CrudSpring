package org.boyar.boyarkin.utils;

import org.boyar.boyarkin.exception.RusphoneViolationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RusPhoneValidation {
    private static final Pattern RUS_PHONE = Pattern.compile("((8|\\+7)-?)?\\(?\\d{3}\\)?-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}-?\\d{1}");

    public static boolean doesNotContainRusPhone(String str) throws RusphoneViolationException {
        Matcher matcher = RUS_PHONE.matcher(str);
        if (matcher.find()) {
            throw new RusphoneViolationException("No russians phone in a description");
        }
        return true;
    }
}
