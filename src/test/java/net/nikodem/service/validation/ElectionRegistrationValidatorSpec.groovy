package net.nikodem.service.validation

import net.nikodem.model.dto.ElectionRegistrationRequest
import net.nikodem.model.exception.*
import net.nikodem.repository.VoterRepository
import spock.lang.Specification

class ElectionRegistrationValidatorSpec extends Specification {
    ElectionRegistrationValidator validator
    VoterRepository voterRepositoryMock

    def setup() {
        voterRepositoryMock = Mock(VoterRepository)
        validator = new ElectionRegistrationValidator()
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
                new ElectionRegistrationRequest("", "", [], []),
                new ElectionRegistrationRequest(null, "", [], [])
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
                new ElectionRegistrationRequest("01", "", [], [])
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
                new ElectionRegistrationRequest("01", " ", [" "], [])
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
                new ElectionRegistrationRequest("01", " ", [" ", "  "], [" "])
        ]
    }

    def "Any duplicated answer causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(DuplicatedAnswersException)
        where:
        request << [
                new ElectionRegistrationRequest("01", " ", ["a", "a", "b"], [" ", " ", " a "])
        ]
    }

    def "Any duplicated username causes exception"() {
        when:
        validator.validate(request)
        then:
        0 * voterRepositoryMock.existsByUsername(_)
        thrown(DuplicatedVotersException)
        where:
        request << [
                new ElectionRegistrationRequest("01", " ", ["a", "b"], ["Alice", "Bob", "Alice"])
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
                new ElectionRegistrationRequest("01", " ", ["a", "b"], ["Alice", "Bob", "nonexistent"])
        ]
    }

    def "Correct example passes"() {
        when:
        validator.validate(request)
        then:
        3 * voterRepositoryMock.existsByUsername(_) >> true
        where:
        request << [
                new ElectionRegistrationRequest("01", " ", ["a", "b"], ["Alice", "Bob", "Cecil"])
        ]
    }


}
