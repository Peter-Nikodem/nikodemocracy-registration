package net.nikodem.dbIntegration

import net.nikodem.NikodemocracyRegistrationApplication
import net.nikodem.model.json.ElectionCreationRequest
import net.nikodem.model.json.VoterRegistration
import net.nikodem.repository.AnswerRepository
import net.nikodem.repository.ElectionRepository
import net.nikodem.repository.VoteAuthorizationRepository
import net.nikodem.service.ElectionCreationService
import net.nikodem.service.VoterRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyRegistrationApplication.class)
class CreateElectionUseCaseSpec extends Specification {

    VoterRegistration neosRegistration = new VoterRegistration('Neo', 'Password', 'Password')
    VoterRegistration trinitysRegistration = new VoterRegistration('Trinity', 'Password', 'Password')
    VoterRegistration cyphersRegistration = new VoterRegistration('Cypher', 'Password', 'Password')

    ElectionCreationRequest matrixOne = new ElectionCreationRequest("This is your last chance. After this, there's no turning back.",
            "You take the blue pill, the story ends; you wake up in your bed and believe whatever you want to believe. " +
                    "You take the red pill, you stay in Wonderland and I'll show you how deep the rabbit hole goes",
            ["Blue pill", "Red pill"],
            ["Neo", "Trinity", "Cypher"]
    )


    @Autowired
    VoterRegistrationService voterRegistrationService
    @Autowired
    ElectionCreationService electionCreationService
    @Autowired
    ElectionRepository electionRepository
    @Autowired
    AnswerRepository answerRepository
    @Autowired
    VoteAuthorizationRepository voteAuthorizationRepository

    @Transactional
    @Rollback
    def "User creates an election"() {
        when:
        voterRegistrationService.registerVoter(neosRegistration)
        voterRegistrationService.registerVoter(trinitysRegistration)
        voterRegistrationService.registerVoter(cyphersRegistration)
        electionCreationService.createElection(matrixOne)
        then:
        answerRepository.count() == 2
        voteAuthorizationRepository.count() == 3
        electionRepository.count() == 1
    }

}
