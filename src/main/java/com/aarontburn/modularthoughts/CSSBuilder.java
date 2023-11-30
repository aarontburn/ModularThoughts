package com.aarontburn.modularthoughts;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class CSSBuilder {

    private final StringBuilder cssStringBuilder = new StringBuilder();

    public CSSBuilder setBorderColor(final String s) {
        cssStringBuilder.append("-fx-border-color: ").append(s).append(";");
        return this;
    }

    public CSSBuilder setBorderColor(final int r, final int g, final int b) {
        cssStringBuilder.append(String.format("-fx-border-color: rgb(%s, %s, %s);", r, g, b));
        return this;
    }

    public CSSBuilder setBorderRadius(final double radius) {
        cssStringBuilder.append("-fx-border-radius: ").append(radius).append(";");
        return this;
    }

    public CSSBuilder setTextFill(final String s) {
        cssStringBuilder.append("-fx-text-fill: ").append(s).append(";");
        return this;
    }

    public CSSBuilder setTextFill(final int r, final int g, final int b) {
        cssStringBuilder.append(String.format("-fx-text-fill: rgb(%s, %s, %s);", r, g, b));
        return this;
    }

    public CSSBuilder setFontSize(final int size) {
        cssStringBuilder.append("-fx-font-size: ").append(size).append(";");
        return this;
    }

    public CSSBuilder setPadding(final double padding) {
        cssStringBuilder.append("-fx-padding: ").append(padding).append(";");
        return this;
    }

    public String finish() {
        return cssStringBuilder.toString();
    }

    public static Node styleNode(final Node theNode, final CSSBuilder theCSS) {
        return styleNode(theNode, theCSS.finish());
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


}
