package net.nikodem.integration

import net.nikodem.NikodemocracyRegistrationApplication
import net.nikodem.model.dto.VoteAuthorizationRequest
import net.nikodem.model.exception.UnauthorizedVoterException
import net.nikodem.repository.VoterRepository
import net.nikodem.service.ElectionRegistrationService
import net.nikodem.service.ElectionTransferService
import net.nikodem.service.VoteAuthorizationService
import net.nikodem.service.VoterRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static net.nikodem.testdata.ExampleElections.A_FEW_GOOD_MEN_PROBLEM
import static net.nikodem.testdata.ExampleVoter.*

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyRegistrationApplication.class)
class AuthorizeVoteUseCaseSpec extends Specification {

    @Autowired
    VoterRegistrationService voterRegistrationService
    @Autowired
    ElectionRegistrationService electionCreationService
    @Autowired
    VoteAuthorizationService voteAuthorizationService

    @Autowired
    VoterRepository voterRepository

    ElectionTransferService electionTransferServiceMock


    def setup() {
        electionTransferServiceMock = Mock(ElectionTransferService)
        electionCreationService.electionTransferService = electionTransferServiceMock
        _ * electionTransferServiceMock.postElection(_)
    }

    @Transactional
    @Rollback
    def "Voter retrieves his voterKey"() {
        given:
        GOOD_GUYS.forEach { voterRegistrationService.registerVoter(it.voterRegistrationRequest) }
        electionCreationService.registerElection(A_FEW_GOOD_MEN_PROBLEM)
        when:
        def response = voteAuthorizationService.authorize(new VoteAuthorizationRequest(BOB.username, BOB.password, A_FEW_GOOD_MEN_PROBLEM.electionId))
        then:
        response.voterKey.length() == 16
    }

    @Transactional
    @Rollback
    def "Voter with case error in password fails to retrieve his voterKey"() {
        given:
        GOOD_GUYS.forEach { voterRegistrationService.registerVoter(it.voterRegistrationRequest) }
        electionCreationService.registerElection(A_FEW_GOOD_MEN_PROBLEM)
        when:
        voteAuthorizationService.authorize(new VoteAuthorizationRequest(DENIS.username, DENIS.password.toUpperCase(), A_FEW_GOOD_MEN_PROBLEM.electionId))
        then:
        thrown(UnauthorizedVoterException)
    }

    @Transactional
    @Rollback
    def "Voter with incorrect password fails to retrieve his voterKey"() {
        given:
        GOOD_GUYS.forEach { voterRegistrationService.registerVoter(it.voterRegistrationRequest) }
        electionCreationService.registerElection(A_FEW_GOOD_MEN_PROBLEM)
        when:
        voteAuthorizationService.authorize(new VoteAuthorizationRequest(DENIS.username, DENIS.password.substring(3), A_FEW_GOOD_MEN_PROBLEM.electionId))
        then:
        thrown(UnauthorizedVoterException)
    }

    @Transactional
    @Rollback
    def "Uninvited voters fails to retrieve a voterKey"() {
        given:
        GOOD_GUYS.forEach { voterRegistrationService.registerVoter(it.voterRegistrationRequest) }
        electionCreationService.registerElection(A_FEW_GOOD_MEN_PROBLEM)
        when:
        voteAuthorizationService.authorize(new VoteAuthorizationRequest(EVE.username, EVE.password, A_FEW_GOOD_MEN_PROBLEM.electionId))
        then:
        thrown(UnauthorizedVoterException)
    }

    @Transactional
    @Rollback
    def "Voters fails retrieve a voterKey to a nonexistent election "() {
        given:
        GOOD_GUYS.forEach { voterRegistrationService.registerVoter(it.voterRegistrationRequest) }
        electionCreationService.registerElection(A_FEW_GOOD_MEN_PROBLEM)
        when:
        voteAuthorizationService.authorize(new VoteAuthorizationRequest(DENIS.username, DENIS.password, "made up election id"))
        then:
        thrown(UnauthorizedVoterException)
    }


    def requiredVotersAreAlreadyRegistered() {
        GOOD_GUYS.forEach { voterRegistrationService.registerVoter(it.voterRegistrationRequest) }
    }
}
