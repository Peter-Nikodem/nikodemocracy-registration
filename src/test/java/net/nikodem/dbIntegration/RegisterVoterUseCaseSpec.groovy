package net.nikodem.dbIntegration

import net.nikodem.NikodemocracyRegistrationApplication
import net.nikodem.model.exception.voters.UsernameAlreadyExistsException
import net.nikodem.model.json.VoterRegistration
import net.nikodem.repository.VoterRepository
import net.nikodem.service.VoterRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyRegistrationApplication.class)
class RegisterVoterUseCaseSpec extends Specification {

    @Autowired
    VoterRegistrationService voterRegistrationService;

    @Autowired
    VoterRepository voterRepository

    VoterRegistration alicesRegistration = new VoterRegistration('Alice', 'Password', 'Password')

    def setup() {
        voterRepository.deleteAll()
    }

    def "User registers as a new voter"() {
        when:
        voterRegistrationService.registerVoter(alicesRegistration)
        then:
        voterRepository.existsByUsername('Alice')
    }

    def "User tries to register with an existing username and fails"() {
        given:
        voterRegistrationService.registerVoter(alicesRegistration)
        when:
        voterRegistrationService.registerVoter(alicesRegistration)
        then:
        thrown(UsernameAlreadyExistsException)
    }


}