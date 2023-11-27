package com.aarontburn.modularthoughts;

import java.util.regex.Pattern;


public class SandBox {

    public static void main(final String[] args) {
        var b = "12 34".matches("^(?!.*(\\d).*\\1)[1-4\\s]+$");

        System.out.println(b);

    }


}
