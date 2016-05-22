package net.nikodem.service.validation

import net.nikodem.model.dto.VoteAuthorizationRequest
import net.nikodem.model.entity.ElectionEntity
import net.nikodem.model.entity.VoteAuthorizationEntity
import net.nikodem.model.entity.VoterEntity
import net.nikodem.model.exception.EmptyElectionIdException
import net.nikodem.model.exception.EmptyPasswordException
import net.nikodem.model.exception.EmptyUsernameException
import net.nikodem.model.exception.UnauthorizedVoterException
import net.nikodem.repository.VoteAuthorizationRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

import static net.nikodem.testdata.ExampleElections.CYCLOPS_CONUNDRUM
import static net.nikodem.testdata.ExampleVoter.ALICE


class VoteAuthorizationValidatorSpec extends Specification {

    VoteAuthorizationValidator validator = new VoteAuthorizationValidator()
    VoteAuthorizationRepository voteAuthorizationRepositoryMock = Mock(VoteAuthorizationRepository)
    BCryptPasswordEncoder passwordEncoderMock = Mock(BCryptPasswordEncoder)
    VoteAuthorizationEntity VOTE_AUTHORIZATION_ENTITY_EXAMPLE = new VoteAuthorizationEntity('stubVoterKey', new VoterEntity(ALICE.username, 'encodedPassword'), new ElectionEntity())

    def setup() {
        validator.voteAuthorizationRepository = voteAuthorizationRepositoryMock
        validator.passwordEncoder = passwordEncoderMock
    }

    def "Empty username causes exception"() {
        when:
        VoteAuthorizationRequest authorizationRequest = new VoteAuthorizationRequest('', 'Password', 'ElectionId')
        validator.validate(authorizationRequest)
        then:
        0 * voteAuthorizationRepositoryMock._(*_)
        thrown EmptyUsernameException
    }

    def "Empty password causes exception"() {
        when:
        VoteAuthorizationRequest authorizationRequest = new VoteAuthorizationRequest(ALICE.username, '', 'ElectionId')
        validator.validate(authorizationRequest)
        then:
        0 * voteAuthorizationRepositoryMock._(*_)
        thrown EmptyPasswordException
    }

    def "Empty electionId causes exception"() {
        when:
        VoteAuthorizationRequest authorizationRequest = new VoteAuthorizationRequest(ALICE.username, ALICE.password, '')
        validator.validate(authorizationRequest)
        then:
        0 * voteAuthorizationRepositoryMock._(*_)
        thrown EmptyElectionIdException
    }

    def "Nonexistent election or uninvited voter cause exception"() {
        when:
        VoteAuthorizationRequest authorizationRequest = ALICE.getVoteAuthorizationRequestFor(CYCLOPS_CONUNDRUM.electionId)
        validator.validate(authorizationRequest)
        then:
        1 * voteAuthorizationRepositoryMock.findByVoterUsernameAndElectionElectionId(ALICE.username, CYCLOPS_CONUNDRUM.electionId) >> Optional.empty()
        0 * passwordEncoderMock._(*_)
        thrown UnauthorizedVoterException
    }

    def "Invalid password causes exception"() {
        when:
        VoteAuthorizationRequest authorizationRequest = new VoteAuthorizationRequest(ALICE.username, ALICE.password.substring(1), CYCLOPS_CONUNDRUM.electionId)
        validator.validate(authorizationRequest)
        then:
        1 * voteAuthorizationRepositoryMock.findByVoterUsernameAndElectionElectionId(ALICE.username, CYCLOPS_CONUNDRUM.electionId) >> Optional.of(VOTE_AUTHORIZATION_ENTITY_EXAMPLE)
        1 * passwordEncoderMock.matches(ALICE.password.substring(1), 'encodedPassword') >> false
        thrown UnauthorizedVoterException
    }

    def "Request passes otherwise"() {
        when:
        VoteAuthorizationRequest authorizationRequest = new VoteAuthorizationRequest(ALICE.username, ALICE.password, CYCLOPS_CONUNDRUM.electionId)
        validator.validate(authorizationRequest)
        then:
        1 * voteAuthorizationRepositoryMock.findByVoterUsernameAndElectionElectionId(ALICE.username, CYCLOPS_CONUNDRUM.electionId) >> Optional.of(VOTE_AUTHORIZATION_ENTITY_EXAMPLE)
        1 * passwordEncoderMock.matches(ALICE.password, 'encodedPassword') >> true
    }


}
