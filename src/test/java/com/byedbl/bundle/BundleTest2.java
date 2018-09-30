package com.byedbl.bundle;

import java.util.ResourceBundle;

public class BundleTest2 {
    private static final String LSTRING_FILE =
            "LocalStrings";
    private static ResourceBundle lStrings =
            ResourceBundle.getBundle(LSTRING_FILE);

    public static void main(String[] args) {

        lStrings.keySet().forEach(System.out::println);
    }
}
