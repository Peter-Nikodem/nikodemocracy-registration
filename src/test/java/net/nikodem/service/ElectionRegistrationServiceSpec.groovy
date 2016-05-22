package net.nikodem.service

import net.nikodem.model.exception.EmptyQuestionException
import net.nikodem.repository.ElectionRepository
import net.nikodem.repository.VoterRepository
import net.nikodem.service.validation.ElectionRegistrationValidator
import spock.lang.Specification

import static net.nikodem.testdata.ExampleElections.CYCLOPS_CONUNDRUM
import static net.nikodem.testdata.ExampleElections.INVALID_ELECTION_REQUEST

class ElectionRegistrationServiceSpec extends Specification {
    ElectionRegistrationService electionCreationService
    ElectionRegistrationValidator electionValidatorMock

    AnswerService answerServiceMock
    VoteAuthorizationService voteAuthorizationServiceMock
    ElectionRepository electionRepositoryMock
    VoterRepository voterRepositoryMock
    ElectionTransferService electionTransferServiceMock

    def setup() {
        electionRepositoryMock = Mock(ElectionRepository)
        answerServiceMock = Mock(AnswerService)
        voteAuthorizationServiceMock = Mock(VoteAuthorizationService)
        voterRepositoryMock = Mock(VoterRepository)
        electionTransferServiceMock = Mock(ElectionTransferService)
        electionValidatorMock = Mock(ElectionRegistrationValidator)
        electionCreationService = new ElectionRegistrationService()
        electionCreationService.setElectionRepository(electionRepositoryMock)
        electionCreationService.setElectionValidator(electionValidatorMock)
        electionCreationService.setAnswerService(answerServiceMock)
        electionCreationService.setVoteAuthorizationService(voteAuthorizationServiceMock)
        electionCreationService.setElectionTransferService(electionTransferServiceMock)
    }

    def "Registering invalid election"() {
        given:
        1 * electionValidatorMock.validate(INVALID_ELECTION_REQUEST) >> { throw new EmptyQuestionException() }
        when:
        electionCreationService.registerElection(INVALID_ELECTION_REQUEST)
        then:
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        0 * electionTransferServiceMock._(*_)
        thrown(EmptyQuestionException)
    }

    def "Registering valid election"() {
        when:
        electionCreationService.registerElection(CYCLOPS_CONUNDRUM)
        then:
        1 * electionValidatorMock.validate(CYCLOPS_CONUNDRUM)
        1 * electionRepositoryMock.save(_)
        1 * answerServiceMock.saveAnswers(_, CYCLOPS_CONUNDRUM.answers)
        1 * voteAuthorizationServiceMock.createAndSaveAuthorizations(_, CYCLOPS_CONUNDRUM.invitedVoters)
        1 * electionTransferServiceMock.postElection(_)
    }
}
