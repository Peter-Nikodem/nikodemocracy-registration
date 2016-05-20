package net.nikodem.service

import spock.lang.Ignore
import spock.lang.Specification

class VoterKeyGeneratorTest extends Specification {

    def 'Generated voter key has 16 alphanumeric characters'() {
        when:
        def generator = new VoterKeyGenerator()
        def sizeOfFirstKey = generator.generateNextRandomVoterKey().size()
        def sizeOfSecondKey = generator.generateNextRandomVoterKey().size()
        then:
        sizeOfFirstKey == 16
        sizeOfSecondKey == 16
    }

    @Ignore
    def "Voter key generation is random enough"(){
        given:  Set set = new HashSet()
        def N = 500_000
        def generator = new VoterKeyGenerator()
        when:   N.times {
            set.add(generator.generateNextRandomVoterKey())
        }
        then:   set.size() == N
    }
}
