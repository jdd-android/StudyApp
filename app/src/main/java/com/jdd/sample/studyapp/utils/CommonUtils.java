package com.jdd.sample.studyapp.utils;

/**
 * @author lc. 2018-01-22 13:56
 * @since 0.5.1
 */

public class CommonUtils {

    public static int string2int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            // parse fail, return default 0
            return 0;
        }
    }

}
