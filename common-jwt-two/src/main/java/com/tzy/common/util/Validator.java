package com.tzy.common.util;

public class Validator {

    public static boolean valid(String parameter) {
        return valid((Object) parameter) && parameter.length() > 0;
    }

    public static boolean password(String parameter) {
        return parameter != null && parameter.trim().length() == 32;
    }

    public static boolean valid(Object parameter) {
        return parameter != null;
    }

    public static boolean valid(Object[] parameter) {
        return parameter != null && parameter.length > 0;
    }

    public static boolean valid(Boolean parameter) {
        return valid((Object) parameter);
    }

    public static boolean valid(Integer parameter) {
        return valid((Object) parameter) && parameter.intValue() >= 0;
    }

    public static boolean valid(Long parameter) {
        return valid((Object) parameter) && parameter.longValue() > 0;
    }

    public static boolean valid(Double parameter) {
        return valid((Object) parameter);
    }

    public static boolean validLength(String parameter, int length) {
        return valid(parameter) && parameter.length() == length;
    }

    public static boolean correctly(Double parameter) {
        return valid((Object) parameter) && parameter.doubleValue() >= 0;
    }

    public static boolean validPhone(String parameter) {
        return parameter != null && parameter.length() == 11;
    }

    public static boolean validKey(String parameter) {
        return valid((Object) parameter) && parameter.length() == 32;
    }


}
