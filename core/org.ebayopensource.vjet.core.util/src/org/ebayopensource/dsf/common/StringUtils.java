package org.ebayopensource.dsf.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringUtils {

    public static List<String> splitStr(String str, char delimiter) {
        return splitStr(str, delimiter, false);
    }

    /**
     * Splits string by delimiter. If delimiter is the last character
     * of the string, empty substring after that will not be added
     * to the result.
     * 
     * @param str
     * @param delimiter
     * @return
     */
    public static List<String> splitStr(String str, char delimiter, boolean trim) {
        int startPos = 0;
        final List<String> result = new ArrayList<String>();
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c == delimiter) {
                String subStr = str.substring(startPos, i);
                if (trim) {
                    subStr = subStr.trim();
                }
                result.add(subStr);
                startPos = i + 1;
                continue;
            }
        }
        if (startPos < str.length()) {
            String subStr = str.substring(startPos, str.length());
            if (trim) {
                subStr = subStr.trim();
            }
            result.add(subStr);
        }
        return result;
    }

    public static String join(
            final Iterable<String> iterable,
            final String joinString)
        {
            return join(iterable.iterator(), joinString);
        }
        public static String join(
            final Iterator<String> iterator,
            final String joinString)
        {
            final StringBuilder buf = new StringBuilder();
            join(iterator, joinString, buf);
            final String resultString = buf.toString();
            return resultString;
        }

        public static void join(
            final Iterable<String> iterator,
            final String joinString,
            final StringBuilder stringStream)
        {
            join(iterator.iterator(), joinString, stringStream);
        }
        public static void join(
            final Iterator<String> iterator,
            final String joinString,
            final StringBuilder stringStream)
        {
            while (iterator.hasNext()) {
                final String string = iterator.next();
                stringStream.append(string);
                if (iterator.hasNext()){
                    stringStream.append(joinString);
                }
            }
        }
}
