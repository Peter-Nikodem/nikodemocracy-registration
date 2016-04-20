package net.nikodem.util

import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class CollectionUtilsTest extends Specification {

    def "getDuplicates returns duplicates and only duplicates"() {
        expect:
        CollectionUtils.getDuplicates(list) == set;
        where:
        list                              | set
        ['h', 'e', 'l', 'l', 'o']         | ['l'].toSet()
        ['aaa', 'aa', 'aaa', 'baa', 'ab'] | ['aaa'].toSet()
        [1, 2, 3, 4, 5, 4, 3, 2]          | [2, 3, 4].toSet()
        [1, 2, 3, 4, 5]                   | [].toSet()
    }
}
