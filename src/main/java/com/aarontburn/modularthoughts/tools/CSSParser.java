package com.aarontburn.modularthoughts.tools;

import java.util.HashMap;
import java.util.Map;

public class CSSParser {


    public static CSSBuilder parse(final String theCssString) {
        if (theCssString == null || theCssString.isEmpty()) {
            return new CSSBuilder();
        }
        final Map<String, String> cssValues = new HashMap<>();

        final String[] split = theCssString.split(";");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }

        for (final String s : split) {
            final String[] css = s.split(":");
            cssValues.put(css[0].trim(), css[1].trim());
        }
        return new CSSBuilder().fromMap(cssValues);
    }


}
