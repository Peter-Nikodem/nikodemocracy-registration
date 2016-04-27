package net.nikodem.dbIntegration

import net.nikodem.NikodemocracyRegistrationApplication
import net.nikodem.model.json.ElectionCreationRequest
import net.nikodem.model.json.VoteAuthorizationRequest
import net.nikodem.model.json.VoterRegistrationRequest
import net.nikodem.repository.VoteAuthorizationRepository
import net.nikodem.service.ElectionCreationService
import net.nikodem.service.VoteAuthorizationService
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
class VoteAuthorizationUseCaseSpec extends Specification {

    VoterRegistrationRequest neosRegistration = new VoterRegistrationRequest('Neo', 'Password', 'Password')
    VoterRegistrationRequest trinitysRegistration = new VoterRegistrationRequest('Trinity', 'Password', 'Password')
    VoterRegistrationRequest cyphersRegistration = new VoterRegistrationRequest('Cypher', 'Password', 'Password')

    ElectionCreationRequest matrixOne = new ElectionCreationRequest("ElectionId",
            "Question",
            ["Blue pill", "Red pill"],
            ["Neo", "Trinity", "Cypher"]
    )

    @Autowired
    VoterRegistrationService voterRegistrationService
    @Autowired
    ElectionCreationService electionCreationService
    @Autowired
    VoteAuthorizationService voteAuthorizationService

    @Autowired
    VoteAuthorizationRepository repository

    @Transactional
    @Rollback
    def "Voter retrieves his voterKey"(){
        when:
        voterRegistrationService.registerVoter(neosRegistration)
        voterRegistrationService.registerVoter(trinitysRegistration)
        voterRegistrationService.registerVoter(cyphersRegistration)
        electionCreationService.createElection(matrixOne)
        def response = voteAuthorizationService.authorize(new VoteAuthorizationRequest('Neo','Password','ElectionId'))
        then:
        response.voterKey.length() == 16
    }
}
