package net.nikodem.service.validation

import net.nikodem.model.exception.election.DuplicateAnswersException
import net.nikodem.model.exception.election.DuplicateVotersException
import net.nikodem.model.exception.election.EmptyElectionIdException
import net.nikodem.model.exception.election.EmptyQuestionException
import net.nikodem.model.exception.election.NotEnoughAnswersException
import net.nikodem.model.exception.election.NotEnoughVotersException
import net.nikodem.model.exception.election.VoterDoesNotExistException
import net.nikodem.model.json.ElectionCreationRequest
import net.nikodem.repository.VoterRepository
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class ElectionValidatorSpec extends Specification {
    ElectionValidator validator
    VoterRepository voterRepositoryMock

    def setup() {
        voterRepositoryMock = Mock(VoterRepository)
        validator = new ElectionValidator()
        validator.setVoterRepository(voterRepositoryMock)
    }

    def "Empty electionId causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(EmptyElectionIdException)
        where:
        request << [
                new ElectionCreationRequest("", "", [], []),
                new ElectionCreationRequest(null, "", [], [])
        ]
    }

    def "Empty question causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(EmptyQuestionException)
        where:
        request << [
                new ElectionCreationRequest("01", "", [], [])
        ]
    }

    def "Less than two answers causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(NotEnoughAnswersException)
        where:
        request << [
                new ElectionCreationRequest("01", " ", [" "], [])
        ]
    }

    def "Less than three voters causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(NotEnoughVotersException)
        where:
        request << [
                new ElectionCreationRequest("01", " ", [" ", "  "], [" "])
        ]
    }

    def "Any duplicated answer causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(DuplicateAnswersException)
        where:
        request << [
                new ElectionCreationRequest("01", " ", ["a", "a", "b"], [" ", " "])
        ]
    }

    def "Any duplicated username causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(DuplicateVotersException)
        where:
        request << [
                new ElectionCreationRequest("01", " ", ["a", "b"], ["Alice", "Bob", "Alice"])
        ]
    }

    def "Including nonexistent voters causes exception"() {
        when:
        validator.validate(request)
        then:
        2 * voterRepositoryMock.existsByUsername(!'nonexistent') >> true
        1 * voterRepositoryMock.existsByUsername('nonexistent') >> false
        thrown(VoterDoesNotExistException)
        where:
        request << [
                new ElectionCreationRequest("01", " ", ["a", "b"], ["Alice", "Bob", "nonexistent"])
        ]
    }

    def "Correct example passes"() {
        when:
        validator.validate(request)
        then:
        3 * voterRepositoryMock.existsByUsername(_) >> true
        where:
        request << [
                new ElectionCreationRequest("01", " ", ["a", "b"], ["Alice", "Bob", "Cecil"])
        ]
    }


}
