package com.aarontburn.modularthoughts;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Helper {


    public static String getNumberSuffix(final int num) {
        if (!((num > 10) && (num < 19))) {
            return switch (num % 10) {
                case 1 -> "st";
                case 2 -> "nd";
                case 3 -> "rd";
                default -> "th";
            };
        }
        return "th";

    }

    public static Node setAnchor(final Node node,
                                 final Number top,
                                 final Number bottom,
                                 final Number left,
                                 final Number right) {

        AnchorPane.setTopAnchor(node, top == null ? null : top.doubleValue());
        AnchorPane.setBottomAnchor(node, bottom == null ? null : bottom.doubleValue());
        AnchorPane.setLeftAnchor(node, left == null ? null : left.doubleValue());
        AnchorPane.setRightAnchor(node, right == null ? null : right.doubleValue());
        return node;
    }

}
