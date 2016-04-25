package net.nikodem.service


import net.nikodem.model.exception.voter.EmptyPasswordException
import net.nikodem.model.exception.voter.EmptyUsernameException
import net.nikodem.model.exception.voter.RepeatedPasswordDoesNotMatchException
import net.nikodem.model.exception.voter.UsernameAlreadyExistsException
import net.nikodem.model.json.VoterRegistration
import net.nikodem.repository.VoterRepository
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class VoterRegistrationServiceSpec extends Specification {
    VoterRegistrationService registrationService
    VoterRepository voterRepositoryMock

    def setup() {
        voterRepositoryMock = Mock(VoterRepository)
        registrationService = new VoterRegistrationService()
        registrationService.setVoterRepository(voterRepositoryMock)
    }

    def "Registering voter with empty username should throw exception"() {
        when:
        VoterRegistration voter = new VoterRegistration('', 'Password', 'Password')
        registrationService.registerVoter(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(EmptyUsernameException)
    }

    def "Registering voter with empty password should throw exception"() {
        when:
        VoterRegistration voter = new VoterRegistration('Alice', '', ' ');
        registrationService.registerVoter(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(EmptyPasswordException)
    }

    def "Registering voter with with unmatching passwords should throw exception"() {
        when:
        VoterRegistration voter = new VoterRegistration('Alice', 'Password', 'Passwor_')
        registrationService.registerVoter(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(RepeatedPasswordDoesNotMatchException)
    }

    def "Registering voter with already existing username should throw exception"() {
        when:
        VoterRegistration voter = new VoterRegistration('Alice', 'Password', 'Password')
        registrationService.registerVoter(voter)
        then:
        1 * voterRepositoryMock.existsByUsername('Alice') >> true
        0 * voterRepositoryMock.save(_)
        thrown(UsernameAlreadyExistsException)
    }

    def "Registering voter with valid information should store username and encrypted password"() {
        when:
        VoterRegistration voter = new VoterRegistration('Alice', 'Password', 'Password')
        registrationService.registerVoter(voter)
        then:
        1 * voterRepositoryMock.existsByUsername('Alice') >> false
        1 * voterRepositoryMock.save({
            it.username == 'Alice' && it.password != 'Password'
        })
    }


}
