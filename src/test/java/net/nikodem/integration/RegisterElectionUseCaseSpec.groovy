package net.nikodem.integration

import net.nikodem.NikodemocracyRegistrationApplication
import net.nikodem.repository.AnswerRepository
import net.nikodem.repository.ElectionRepository
import net.nikodem.repository.VoteAuthorizationRepository
import net.nikodem.service.ElectionRegistrationService
import net.nikodem.service.ElectionTransferService
import net.nikodem.service.VoterRegistrationService
import net.nikodem.testdata.ExampleElections
import net.nikodem.testdata.ExampleVoter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static ExampleElections.CYCLOPS_CONUNDRUM
import static ExampleVoter.EVERYONE

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyRegistrationApplication.class)
class RegisterElectionUseCaseSpec extends Specification {

    @Autowired
    VoterRegistrationService voterRegistrationService
    @Autowired
    ElectionRegistrationService electionRegisterService
    @Autowired
    ElectionRepository electionRepository
    @Autowired
    AnswerRepository answerRepository
    @Autowired
    VoteAuthorizationRepository voteAuthorizationRepository

    ElectionTransferService electionTransferServiceMock

    def setup() {
        electionTransferServiceMock = Mock(ElectionTransferService)
        electionRegisterService.electionTransferService = electionTransferServiceMock
    }

    @Transactional
    @Rollback
    def "Election is correctly stored"() {
        given:
        invitedVotersAreAlreadyRegistered()
        electionTransferServiceIsMocked()
        when:
        electionRegisterService.registerElection(CYCLOPS_CONUNDRUM)
        then:
        electionDataIsStored()
    }

    def electionTransferServiceIsMocked() {
        1 * electionTransferServiceMock.postElection(_)
    }

    def invitedVotersAreAlreadyRegistered() {
        EVERYONE.forEach { voterRegistrationService.registerVoter(it.voterRegistrationRequest) }
    }

    def electionDataIsStored() {
        electionRepository.count() == 1
        answerRepository.count() == CYCLOPS_CONUNDRUM.answers.size()
        voteAuthorizationRepository.count() == CYCLOPS_CONUNDRUM.invitedVoters.size()
    }
}
