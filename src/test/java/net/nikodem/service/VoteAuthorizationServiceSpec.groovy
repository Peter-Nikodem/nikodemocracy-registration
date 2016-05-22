package net.nikodem.service

import net.nikodem.model.entity.ElectionEntity
import net.nikodem.model.entity.VoteAuthorizationEntity
import net.nikodem.model.entity.VoterEntity
import net.nikodem.model.exception.UnauthorizedVoterException
import net.nikodem.repository.VoteAuthorizationRepository
import net.nikodem.service.validation.VoteAuthorizationValidator
import spock.lang.Specification

import static net.nikodem.testdata.ExampleElections.CYCLOPS_CONUNDRUM
import static net.nikodem.testdata.ExampleVoter.ALICE
import static net.nikodem.testdata.ExampleVoter.INVALID

class VoteAuthorizationServiceSpec extends Specification {
    VoteAuthorizationService authorizationService = new VoteAuthorizationService()
    VoteAuthorizationValidator validatorMock = Mock(VoteAuthorizationValidator)
    VoteAuthorizationRepository authorizationRepositoryMock = Mock(VoteAuthorizationRepository)

    VoteAuthorizationEntity VOTE_AUTHORIZATION_ENTITY_EXAMPLE = new VoteAuthorizationEntity('stubVoterKey', new VoterEntity(ALICE.username, 'encodedPassword'), new ElectionEntity())

    def setup() {
        authorizationService.voteAuthorizationValidator = validatorMock
        authorizationService.voteAuthorizationRepository = authorizationRepositoryMock
    }

    def "Requesting vote authorization with invalid details"() {
        given:
        1 * validatorMock.validate(INVALID.getVoteAuthorizationRequestFor(CYCLOPS_CONUNDRUM.electionId)) >> {
            throw new UnauthorizedVoterException()
        }
        when:
        authorizationService.authorize(INVALID.getVoteAuthorizationRequestFor(CYCLOPS_CONUNDRUM.electionId))
        then:
        0 * authorizationRepositoryMock.findByVoterUsernameAndElectionElectionId(*_)
        thrown UnauthorizedVoterException
    }

    def "Requesting vote authorization with valid details"() {
        given:
        1 * validatorMock.validate(ALICE.getVoteAuthorizationRequestFor(CYCLOPS_CONUNDRUM.electionId))
        1 * authorizationRepositoryMock.findByVoterUsernameAndElectionElectionId(ALICE.username, CYCLOPS_CONUNDRUM.electionId) >> Optional.of(VOTE_AUTHORIZATION_ENTITY_EXAMPLE)
        when:
        def result = authorizationService.authorize(ALICE.getVoteAuthorizationRequestFor(CYCLOPS_CONUNDRUM.electionId))
        then:
        result.voterKey == 'stubVoterKey'
    }


}
