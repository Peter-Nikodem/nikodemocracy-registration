package net.nikodem.util

import spock.lang.Specification

class CollectionUtilsTest extends Specification {

    def "getDuplicates returns duplicates and only duplicates"() {
        expect:
        CollectionUtils.getDuplicates(list) == setOfNonUniqueElements;
        where:
        list                              | setOfNonUniqueElements
        ['h', 'e', 'l', 'l', 'o']         | ['l'].toSet()
        ['aaa', 'aa', 'aaa', 'baa', 'ab'] | ['aaa'].toSet()
        [1, 2, 3, 4, 5, 4, 3, 2]          | [2, 3, 4].toSet()
        [1, 2, 3, 4, 5]                   | [].toSet()
    }
}
