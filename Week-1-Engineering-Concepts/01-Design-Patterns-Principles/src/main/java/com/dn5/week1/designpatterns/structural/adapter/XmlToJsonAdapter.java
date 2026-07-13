package com.dn5.week1.designpatterns.structural.adapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Adapter: makes the legacy {@link XmlDataProvider} usable wherever a
 * {@link JsonDataProvider} is expected, by converting its simple
 * {@code <tag>value</tag>} pairs into a JSON-ish object string.
 */
public class XmlToJsonAdapter implements JsonDataProvider {

    private static final Pattern TAG_PATTERN = Pattern.compile("<(\\w+)>([^<]*)</\\1>");

    private final XmlDataProvider legacyProvider;

    public XmlToJsonAdapter(XmlDataProvider legacyProvider) {
        this.legacyProvider = legacyProvider;
    }

    @Override
    public String getJsonData() {
        String xml = legacyProvider.getXmlData();
        Matcher matcher = TAG_PATTERN.matcher(xml);
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        while (matcher.find()) {
            if (!first) {
                json.append(",");
            }
            String key = matcher.group(1);
            String value = matcher.group(2);
            json.append("\"").append(key).append("\":");
            if (value.matches("-?\\d+(\\.\\d+)?")) {
                json.append(value);
            } else {
                json.append("\"").append(value).append("\"");
            }
            first = false;
        }
        json.append("}");
        return json.toString();
    }
}
