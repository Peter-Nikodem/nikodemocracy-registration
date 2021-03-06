package net.nikodem.integration

import net.nikodem.NikodemocracyRegistrationApplication
import net.nikodem.model.dto.VoterRegistrationRequest
import net.nikodem.model.exception.UsernameAlreadyExistsException
import net.nikodem.repository.VoterRepository
import net.nikodem.service.VoterRegistrationService
import net.nikodem.testdata.ExampleVoter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyRegistrationApplication.class)
class RegisterVoterUseCaseSpec extends Specification {

    @Autowired
    VoterRegistrationService voterRegistrationService

    @Autowired
    VoterRepository voterRepository

    VoterRegistrationRequest alicesRegistration = ExampleVoter.ALICE.voterRegistrationRequest

    @Transactional
    @Rollback
    def "User registers as a new voter"() {
        when:
        voterRegistrationService.registerVoter(alicesRegistration)
        then:
        voterRepository.existsByUsername('Alice')
    }

    @Transactional
    @Rollback
    def "User tries to register with an existing username and fails"() {
        given:
        voterRegistrationService.registerVoter(alicesRegistration)
        when:
        voterRegistrationService.registerVoter(alicesRegistration)
        then:
        thrown(UsernameAlreadyExistsException)
    }
}
