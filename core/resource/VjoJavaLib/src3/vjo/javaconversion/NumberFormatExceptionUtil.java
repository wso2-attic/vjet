package vjo.javaconversion;

import java.lang.NumberFormatException ;

public final class NumberFormatExceptionUtil {
    public static NumberFormatException forInputString(String s) {
        return new NumberFormatException("For input string: \"" + s + "\"");
    }
}
