package net.nikodem.service


import net.nikodem.model.exception.EmptyPasswordException
import net.nikodem.model.exception.EmptyUsernameException
import net.nikodem.model.exception.RepeatedPasswordDoesNotMatchException
import net.nikodem.model.exception.UsernameAlreadyExistsException
import net.nikodem.model.json.VoterRegistrationRequest
import net.nikodem.repository.VoterRepository
import net.nikodem.service.validation.VoterValidator
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class VoterRegistrationServiceSpec extends Specification {
    VoterRegistrationService registrationService
    VoterRepository voterRepositoryMock
    VoterValidator voterValidator
    BCryptPasswordEncoder passwordEncoder;

    def setup() {
        voterRepositoryMock = Mock(VoterRepository)
        registrationService = new VoterRegistrationService()
        voterValidator = new VoterValidator()
        voterValidator.setVoterRepository(voterRepositoryMock)
        registrationService.setVoterValidator(voterValidator)
        registrationService.setVoterRepository(voterRepositoryMock)
        passwordEncoder = new BCryptPasswordEncoder()
    }

    def "Registering voter with empty username should throw exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('', 'Password', 'Password')
        registrationService.registerVoter(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(EmptyUsernameException)
    }

    def "Registering voter with empty password should throw exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', '', ' ');
        registrationService.registerVoter(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(EmptyPasswordException)
    }

    def "Registering voter with with unmatching passwords should throw exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', 'Password', 'Passwor_')
        registrationService.registerVoter(voter)
        then:
        0 * voterRepositoryMock._(*_)
        thrown(RepeatedPasswordDoesNotMatchException)
    }

    def "Registering voter with already existing username should throw exception"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', 'Password', 'Password')
        registrationService.registerVoter(voter)
        then:
        1 * voterRepositoryMock.existsByUsername('Alice') >> true
        0 * voterRepositoryMock.save(_)
        thrown(UsernameAlreadyExistsException)
    }

    def "Registering voter with valid information should store username and encrypted password"() {
        when:
        VoterRegistrationRequest voter = new VoterRegistrationRequest('Alice', 'Password', 'Password')
        registrationService.registerVoter(voter)
        then:
        1 * voterRepositoryMock.existsByUsername('Alice') >> false
        1 * voterRepositoryMock.save({
            it.username == 'Alice' && it.password != 'Password' && passwordEncoder.matches('Password',it.password)
        })
    }


}
