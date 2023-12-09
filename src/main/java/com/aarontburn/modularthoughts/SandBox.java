package com.aarontburn.modularthoughts;


import com.aarontburn.modularthoughts.tools.CSSParser;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SandBox {

    public static void main(final String[] args) {
        System.out.println(LocalTime.parse("12:59AM", DateTimeFormatter.ofPattern("hh:mma")));


    }



}
