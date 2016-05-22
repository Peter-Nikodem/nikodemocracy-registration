package net.nikodem.service.validation

import net.nikodem.model.dto.VoterRegistrationRequest
import net.nikodem.model.exception.EmptyPasswordException
import net.nikodem.model.exception.EmptyUsernameException
import net.nikodem.model.exception.RepeatedPasswordDoesNotMatchException
import net.nikodem.model.exception.UsernameAlreadyExistsException
import net.nikodem.repository.VoterRepository
import spock.lang.Specification

class VoterRegistrationValidatorSpec extends Specification {

    VoterRegistrationValidator validator = new VoterRegistrationValidator()
    VoterRepository voterRepositoryMock = Mock(VoterRepository)

    def setup() {
        validator.voterRepository = voterRepositoryMock
    }

    def "Empty username causes exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('', 'Password', 'Password')
        validator.validate(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(EmptyUsernameException)
    }

    def "Empty password cause exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', '', ' ');
        validator.validate(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(EmptyPasswordException)
    }

    def "Unmatching passwords cause exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', 'Password', 'Passwor_')
        validator.validate(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(RepeatedPasswordDoesNotMatchException)
    }

    def "Already existing username cause exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', 'Password', 'Password')
        validator.validate(voter)
        then:
        1 * voterRepositoryMock.existsByUsername('Alice') >> true
        thrown(UsernameAlreadyExistsException)
    }

    def "Request passes otherwise"() {
        given:
        1 * voterRepositoryMock.existsByUsername('Alice') >> false
        expect:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', 'Password', 'Password')
        validator.validate(voter)
    }
}
