package com.tzy.common.util.authorization.jwt;

public class UrlUtils {

    public static boolean isLike(String source, String regex) {
        if (source == null || regex == null) return false;
        source = source.toLowerCase();
        regex = regex.toLowerCase();
        return matches(source, regex.replaceAll("(^|([^\\\\]))[\\*]{2,}", "$2*"));
    }

    public static void main(String[] args) {
        System.out.println(isLike("/employee/112122","/employee/**"));
        //数据查出来的放在后面
    }
    private static boolean matches(String source, String regex) {
        if (source.equals(regex) && source.indexOf('\\') < 0) return true;
        int rIdx = 0, sIdx = 0;
        while (rIdx < regex.length() && sIdx < source.length()) {
            char c = regex.charAt(rIdx);
            switch (c) {
                case '*':
                    String tempSource = source.substring(sIdx);
                    String tempRegex = regex.substring(rIdx + 1);
                    for (int j = 0; j <= tempSource.length(); j++) {
                        if (matches(tempSource.substring(j), tempRegex)) {
                            return true;
                        }
                    }
                    return false;
                case '?':
                    break;
                case '\\':
                    c = regex.charAt(++rIdx);
                default:
                    if (source.charAt(sIdx) != c) return false;
            }
            rIdx++;
            sIdx++;
        }
        return source.length() == sIdx &&
                (regex.length() == rIdx ||
                        regex.length() == rIdx + 1 && regex.charAt(rIdx) == '*');
    }
}