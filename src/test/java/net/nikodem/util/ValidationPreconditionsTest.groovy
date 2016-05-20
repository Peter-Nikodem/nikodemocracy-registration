package net.nikodem.util

import spock.lang.Specification

import javax.validation.Validation

class ValidationPreconditionsTest extends Specification {

    def "isNullOrEmpty returns true if string is null or empty"() { //thanks, Magic
        expect:
        ValidationPreconditions.isNullOrEmpty(string) == result
        where:
        string       | result
        "any string" | false
        "  "         | false
        " "          | false
        ""           | true
        null         | true
    }

    def "hasLessThanTwoElements returns false if and only if list has two or more elements"() {
        expect:
        ValidationPreconditions.hasLessThanTwoElements(list) == result
        where:
        list                         | result
        ['alpha', 'beta', 'charlie'] | false
        [1, 3]                       | false
        [new Object()]               | true
        []                           | true
        null                         | true
    }
}
