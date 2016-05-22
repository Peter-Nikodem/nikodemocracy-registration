package net.nikodem.util;

import java.util.*;

public class ValidationPreconditions {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static <T> boolean hasLessThanTwoElements(List<T> list) {
        return hasLessThanNElements(list, 2);
    }

    public static <T> boolean hasLessThanThreeElements(List<T> list) {
        return hasLessThanNElements(list, 3);
    }

    private static <T> boolean hasLessThanNElements(List<T> list, int n) {
        return list == null || list.size() < n;
    }


}
