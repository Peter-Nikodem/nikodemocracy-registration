package net.nikodem.controller

import net.nikodem.TestUtils
import net.nikodem.model.exception.ElectionIdAlreadyExistsException
import net.nikodem.model.exception.ElectionTransferFailedException
import net.nikodem.model.exception.EmptyElectionIdException
import net.nikodem.model.exception.EmptyQuestionException
import net.nikodem.model.exception.NotEnoughAnswersException
import net.nikodem.model.exception.NotEnoughVotersException
import net.nikodem.model.exception.VoterDoesNotExistException
import net.nikodem.model.json.ElectionCreationRequest
import net.nikodem.service.ElectionCreationService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static org.mockito.Matchers.any
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author Peter Nikodem 
 */
class ElectionCreationControllerSpec extends Specification {

    MockMvc mockMvc

    @Mock
    ElectionCreationService electionCreationServiceMock

    @InjectMocks
    ElectionCreationController electionCreationController
    ElectionCreationRequest mockElectionCreation = new ElectionCreationRequest('Choose one, Neo!', 'Red or blue?', ['Red', 'Blue'], ['Neo', 'Trinity', 'Cypher'])

    def setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(electionCreationController).build()
    }

    def "Creating election when details are valid should return HTTP code CREATED"() {
        when:
        doNothing().when(electionCreationServiceMock).createElection(any())
        then:
        performElectionCreationRequest()
                .andExpect(status().isCreated());
    }

    def "Creating election when electionId already exists should return error"() {
        when:
        doThrow(ElectionIdAlreadyExistsException).when(electionCreationServiceMock).createElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('Entered electionId already exists.')
    }

    def "Creating election when electionId is empty should return error"() {
        when:
        doThrow(EmptyElectionIdException).when(electionCreationServiceMock).createElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('ElectionId must not be empty.')
    }

    def "Creating election when question is empty should return error"() {
        when:
        doThrow(EmptyQuestionException).when(electionCreationServiceMock).createElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('Question must not be empty.')
    }

    def "Creating election when there aren't at least two answers should return error"() {
        when:
        doThrow(NotEnoughAnswersException).when(electionCreationServiceMock).createElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('There must be at least two possible answers.')
    }

    def "Creating election when there aren't at least three voters should return error"() {
        when:
        doThrow(NotEnoughVotersException).when(electionCreationServiceMock).createElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('There must be at least three invited voters.')

    }

    def "Creating election when any of the voters does not exist should return error"() {
        when:
        doThrow(new VoterDoesNotExistException(['Link'].toSet())).when(electionCreationServiceMock).createElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('Voters with usernames [Link] not found.')
    }

    def "Failure to transfer created election details to Tabulation Authority returns error"() {
        when:
        doThrow(new ElectionTransferFailedException()).when(electionCreationServiceMock).createElection(any())
        then:
        performElectionCreationRequest()
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$', notNullValue()))
                .andExpect(jsonPath('$.errorMessage', is("Could not transfer the election details to the tabulation authority. Sorry!")));
    }


    def performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs(String errorMessage) {
        performElectionCreationRequest()
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$', notNullValue()))
                .andExpect(jsonPath('$.errorMessage', is(errorMessage)));
    }

    private ResultActions performElectionCreationRequest() {
        mockMvc.perform(post("/elections")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(mockElectionCreation))
        )
    }

}
