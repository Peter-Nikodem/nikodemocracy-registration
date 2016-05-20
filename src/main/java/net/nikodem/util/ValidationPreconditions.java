package net.nikodem.util;

import java.util.*;

public class ValidationPreconditions {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static <T> boolean hasLessThanTwoElements(List<T> list) {
        return list == null || list.size() < 2;
    }

    public static <T> boolean hasLessThanThreeElements(List<T> list){
        return list == null || list.size() <3;
    }


}
