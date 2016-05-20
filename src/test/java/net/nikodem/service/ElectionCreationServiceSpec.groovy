package net.nikodem.service

import net.nikodem.model.exception.DuplicatedAnswersException
import net.nikodem.model.exception.DuplicatedVotersException
import net.nikodem.model.exception.EmptyElectionIdException
import net.nikodem.model.exception.EmptyQuestionException
import net.nikodem.model.exception.NotEnoughAnswersException
import net.nikodem.model.exception.NotEnoughVotersException
import net.nikodem.model.exception.VoterDoesNotExistException
import net.nikodem.model.json.ElectionCreationRequest
import net.nikodem.repository.ElectionRepository
import net.nikodem.repository.VoterRepository
import net.nikodem.service.validation.ElectionValidator
import spock.lang.Specification

class ElectionCreationServiceSpec extends Specification {
    ElectionCreationService electionCreationService
    ElectionValidator electionValidator

    AnswerService answerServiceMock
    VoteAuthorizationService voteAuthorizationServiceMock
    ElectionRepository electionRepositoryMock
    VoterRepository voterRepositoryMock
    ElectionTransferringService electionTransferringServiceMock

    def setup() {
        electionRepositoryMock = Mock(ElectionRepository)
        answerServiceMock = Mock(AnswerService)
        voteAuthorizationServiceMock = Mock(VoteAuthorizationService)
        voterRepositoryMock = Mock(VoterRepository)
        electionTransferringServiceMock = Mock(ElectionTransferringService)
        electionValidator = new ElectionValidator()
        electionValidator.setVoterRepository(voterRepositoryMock)
        electionCreationService = new ElectionCreationService()
        electionCreationService.setElectionRepository(electionRepositoryMock)
        electionCreationService.setElectionValidator(electionValidator)
        electionCreationService.setAnswerService(answerServiceMock)
        electionCreationService.setVoteAuthorizationService(voteAuthorizationServiceMock)
        electionCreationService.setElectionTransferringService(electionTransferringServiceMock)
    }

    def "Creating election with empty electionID throws exception"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('','',[],[])
        electionCreationService.createElection(electionRequest)
        then:
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        thrown(EmptyElectionIdException)
    }

    def "Creating election with empty question throws exception"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('id','',[],[])
        electionCreationService.createElection(electionRequest)
        then:
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        thrown(EmptyQuestionException)
    }

    def "Creating election with less than two answers throws exception"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('id','question?',['onlyOneElement'],[])
        electionCreationService.createElection(electionRequest)
        then:
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        thrown(NotEnoughAnswersException)
    }

    def "Creating election with less than three voter usernames throws exception"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('id','question?',['answer1','answer2'],['voter1','voter2'])
        electionCreationService.createElection(electionRequest)
        then:
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        thrown(NotEnoughVotersException)
    }

    def "Creating election with duplicated answers throws exception"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('id','question?',['sameAnswer','sameAnswer'],['voter1','voter2','voter3'])
        electionCreationService.createElection(electionRequest)
        then:
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        thrown(DuplicatedAnswersException)
    }

    def "Creating election with duplicated voter usernames throws exception"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('id','question?',['answer1','answer2'],['voter1','voter1','voter1','voter2','voter2','voter3'])
        electionCreationService.createElection(electionRequest)
        then:
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        thrown(DuplicatedVotersException)
    }

    def "Creating election with voter usernames that don't exist throws exception"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('id','question?',['a','b'],['a','b','c','d'])
        electionCreationService.createElection(electionRequest)
        then:
        4 * voterRepositoryMock.existsByUsername(*_) >> false
        0 * electionRepositoryMock._(*_)
        0 * answerServiceMock._(*_)
        0 * voteAuthorizationServiceMock._(*_)
        thrown(VoterDoesNotExistException)
    }

    def "Creating election with valid information stores election"(){
        when:
        ElectionCreationRequest electionRequest = new ElectionCreationRequest('id','question?',['answer1','answer2'],['guy1','guy2','guy3'])
        electionCreationService.createElection(electionRequest)
        then:
        3 * voterRepositoryMock.existsByUsername(_) >> true
        1 * electionRepositoryMock.save(_)
        0 * answerServiceMock._(_)
        0 * voteAuthorizationServiceMock._(_)
    }
}
