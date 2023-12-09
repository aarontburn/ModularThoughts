package com.aarontburn.modularthoughts.tools;

import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class CSSBuilder {

    public static final String ACCENT = "accent-color";

    private final Map<String, String> cssMap = new HashMap<>();


    public CSSBuilder() {

    }

    public CSSBuilder(final CSSBuilder otherBuilder) {
        for (final String s : otherBuilder.cssMap.keySet()) {
            this.cssMap.put(s, otherBuilder.cssMap.get(s));
        }
    }

    public CSSBuilder setBorderColor(final String s) {
        cssMap.put("-fx-border-color", s);
        return this;
    }

    public CSSBuilder setBorderColor(final int r, final int g, final int b) {
        cssMap.put("-fx-border-color", String.format("rgb(%s, %s, %s)", r, g, b));
        return this;
    }

    public CSSBuilder setBorderRadius(final double radius) {
        cssMap.put("-fx-border-radius", String.valueOf(radius));
        return this;
    }

    public CSSBuilder setTextFill(final String s) {
        cssMap.put("-fx-text-fill", s);
        return this;
    }

    public CSSBuilder setTextFill(final int r, final int g, final int b) {
        cssMap.put("-fx-text-fill", String.format("rgb(%s, %s, %s);", r, g, b));
        return this;
    }

    public CSSBuilder setFill(final String s) {
        cssMap.put("-fx-fill", s);
        return this;
    }

    public CSSBuilder setFontSize(final int size) {
        cssMap.put("-fx-font-size", String.valueOf(size));
        return this;
    }

    public CSSBuilder setPadding(final double padding) {
        cssMap.put("-fx-padding", String.valueOf(padding));
        return this;
    }

    public CSSBuilder setBackgroundColor(final String s) {
        cssMap.put("-fx-background-color", s);
        return this;
    }

    public CSSBuilder manualSet(final String key, final Object value) {
        this.cssMap.put(key, String.valueOf(value));
        return this;
    }

    public String build() {
        final StringBuilder s = new StringBuilder();
        for (final String key : cssMap.keySet()) {
            s.append(key).append(": ").append(cssMap.get(key)).append(";");
        }
        return s.toString();
    }

    public static Node styleNode(final Node theNode, final CSSBuilder theCSS) {
        return styleNode(theNode, theCSS.build());
    }

    public static Node styleNode(final Node theNode, final String theCSS) {
        theNode.setStyle(theCSS);
        return theNode;
    }

    public static Label styleNewLabel(final String theLabelText, final CSSBuilder theCSS) {
        return (Label) styleNode(new Label(theLabelText), theCSS);
    }

    public static Label styleNewLabel(final String theLabelText, final String theCSS) {
        return (Label) styleNode(new Label(theLabelText), theCSS);
    }

    public static Label styleLabel(final Label theLabel, final CSSBuilder theCSS) {
        return (Label) styleNode(theLabel, theCSS);
    }

    public static Label styleLabel(final Label theLabel, final String theCSS) {
        return (Label) styleNode(theLabel, theCSS);
    }

    CSSBuilder fromMap(final Map<String, String> theParser) {
        final CSSBuilder builder = new CSSBuilder();
        for (final String css : theParser.keySet()) {
            cssMap.put(css, theParser.get(css));
        }
        return builder;
    }


}
