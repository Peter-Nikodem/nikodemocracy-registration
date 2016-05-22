package net.nikodem.service

import net.nikodem.model.exception.EmptyPasswordException
import net.nikodem.repository.VoterRepository
import net.nikodem.service.validation.VoterRegistrationValidator
import net.nikodem.testdata.ExampleVoter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

import static net.nikodem.testdata.ExampleVoter.ALICE

class VoterRegistrationServiceSpec extends Specification {
    VoterRegistrationService registrationService
    VoterRepository voterRepositoryMock
    VoterRegistrationValidator voterValidatorMock
    BCryptPasswordEncoder passwordEncoder;

    def setup() {
        voterValidatorMock = Mock(VoterRegistrationValidator)
        voterRepositoryMock = Mock(VoterRepository)
        registrationService = new VoterRegistrationService()
        registrationService.setVoterValidator(voterValidatorMock)
        registrationService.setVoterRepository(voterRepositoryMock)
        passwordEncoder = new BCryptPasswordEncoder()
    }

    def "Registering voter with invalid information cause exception"() {
        when:
        registrationService.registerVoter(ExampleVoter.INVALID.voterRegistrationRequest)
        then:
        1 * voterValidatorMock.validate(ExampleVoter.INVALID.voterRegistrationRequest) >> {
            throw new EmptyPasswordException()
        }
        0 * voterRepositoryMock.save(_)
        thrown(EmptyPasswordException)
    }

    def "Registering voter with valid information should store username and encrypted password"() {
        when:
        registrationService.registerVoter(ALICE.voterRegistrationRequest)
        then:
        1 * voterValidatorMock.validate(ALICE.voterRegistrationRequest)
        1 * voterRepositoryMock.save({
            it.username == ALICE.username && it.password != ALICE.password && passwordEncoder.matches(ALICE.password, it.password)
        })
    }
}
