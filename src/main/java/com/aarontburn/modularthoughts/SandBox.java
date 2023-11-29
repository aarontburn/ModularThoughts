package com.aarontburn.modularthoughts;


import java.io.File;

public class SandBox {

    public static void main(final String[] args) {

        File f = new File(System.getProperty("user.home") + "/.thoughts/");

        System.out.println(f.mkdir());

    }



}
