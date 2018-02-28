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

    /**
     * 比较两个对象是否相等。
     */
    public static boolean equals(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }
}
