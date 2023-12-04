package com.aarontburn.modularthoughts;


import com.aarontburn.modularthoughts.tools.CSSParser;

public class SandBox {

    public static void main(final String[] args) {
        String s = "accent-text-color: off-white;accent-color: #B0ABED;accent-text-color: off-white;accent-color: #B0ABED;accent-text-color: off-white;accent-color: #B0ABED;";

        CSSParser.parse(s);
    }



}
