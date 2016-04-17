package net.nikodem.integration

import net.nikodem.NikodemocracyRegistrationApplication
import net.nikodem.model.dto.VoterRegistrationDto
import net.nikodem.model.exception.UsernameAlreadyExistsException
import net.nikodem.repository.VoterRepository
import net.nikodem.service.VoterRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyRegistrationApplication.class)
class RegisterVoterUseCaseSpec extends Specification {

    @Autowired
    VoterRegistrationService voterRegistrationService;

    @Autowired
    VoterRepository voterRepository;

    def setup() {
        voterRepository.deleteAll()
    }

    def "User registers as a new voter"() {
        when:
        voterRegistrationService.registerVoter(new VoterRegistrationDto('Alice', 'Password', 'Password'))
        then:
        voterRepository.existsByUsername('Alice')
    }

    def "User tries to register with an existing username and fails"() {
        given:
        voterRegistrationService.registerVoter(new VoterRegistrationDto('Alice', 'Password', 'Password'))
        when:
        voterRegistrationService.registerVoter(new VoterRegistrationDto('Alice', 'Password', 'Password'))
        then:
        thrown(UsernameAlreadyExistsException)
    }


}
