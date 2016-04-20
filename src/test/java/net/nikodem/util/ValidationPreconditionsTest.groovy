package net.nikodem.util

import spock.lang.Specification

import javax.validation.Validation

/**
 * @author Peter Nikodem 
 */
class ValidationPreconditionsTest extends Specification {

    def "isNullOrEmpty test"() {
        expect:
        ValidationPreconditions.isNullOrEmpty(string) | result
        where:
        string       | result
        "any string" | false
        "  "         | false
        " "          | false
        ""           | true
        null         | true
    }

    def "is"() {
        expect:
        ValidationPreconditions.hasLessThanTwoElements(list) | result
        where:
        list                         | result
        ['alpha', 'beta', 'charlie'] | false
        [1, 3]                       | false
        [new Object()]               | true
        []                           | true
        null                         | true
    }
}
