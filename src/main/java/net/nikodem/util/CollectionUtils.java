package net.nikodem.util;

import java.util.*;

/**
 * @author Peter Nikodem
 */
public class CollectionUtils {

    public static <T extends Comparable<T>> Set<T> getDuplicates(List<T> list) {
        List<T> sortedList = getSortedList(list);
        Set<T> duplicates = new HashSet<>();
        for (int index = 1; index < sortedList.size(); index++) {
            T previousElement = sortedList.get(index - 1);
            T currentElement = sortedList.get(index);
            if (currentElement.equals(previousElement)) {
                duplicates.add(currentElement);
            }
        }
        return duplicates;
    }

    public static <T extends Comparable<T>> List<T> getSortedList(List<T> list) {
        List<T> sortedList = new ArrayList<>(list);
        Collections.sort(sortedList);
        return sortedList;
    }
}
