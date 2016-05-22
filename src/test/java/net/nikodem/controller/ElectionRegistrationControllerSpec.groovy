package net.nikodem.controller

import net.nikodem.TestUtils
import net.nikodem.model.dto.ElectionRegistrationRequest
import net.nikodem.model.exception.*
import net.nikodem.service.ElectionRegistrationService
import net.nikodem.testdata.ExampleElections
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ElectionRegistrationControllerSpec extends Specification {

    MockMvc mockMvc

    @Mock
    ElectionRegistrationService electionRegistrationServiceMock

    @InjectMocks
    ElectionRegistrationController electionRegistrationController
    ElectionRegistrationRequest mockElectionRegistration = ExampleElections.CYCLOPS_CONUNDRUM

    def setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(electionRegistrationController).build()
    }

    def "Registering election when details are valid should return HTTP code CREATED"() {
        when:
        doNothing().when(electionRegistrationServiceMock).registerElection(any())
        then:
        performElectionCreationRequest()
                .andExpect(status().isCreated());
    }

    def "Registering election when electionId already exists should return error"() {
        when:
        doThrow(ElectionIdAlreadyExistsException).when(electionRegistrationServiceMock).registerElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('Entered electionId already exists.')
    }

    def "Registering election when electionId is empty should return error"() {
        when:
        doThrow(EmptyElectionIdException).when(electionRegistrationServiceMock).registerElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('ElectionId must not be empty.')
    }

    def "Registering election when question is empty should return error"() {
        when:
        doThrow(EmptyQuestionException).when(electionRegistrationServiceMock).registerElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('Question must not be empty.')
    }

    def "Registering election when there aren't at least two answers should return error"() {
        when:
        doThrow(NotEnoughAnswersException).when(electionRegistrationServiceMock).registerElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('There must be at least two possible answers.')
    }

    def "Registering election when there aren't at least three voters should return error"() {
        when:
        doThrow(NotEnoughVotersException).when(electionRegistrationServiceMock).registerElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('There must be at least three invited voters.')

    }

    def "Registering election when any of the voters does not exist should return error"() {
        when:
        doThrow(new VoterDoesNotExistException(['Link'].toSet())).when(electionRegistrationServiceMock).registerElection(any())
        then:
        performBadElectionCreationRequestAndVerifyThatReturnedErrorMessageIs('Voters with usernames [Link] not found.')
    }

    def "Failure to transfer created election details to Tabulation Authority returns error"() {
        when:
        doThrow(new ElectionTransferFailedException()).when(electionRegistrationServiceMock).registerElection(any())
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
                .content(TestUtils.convertObjectToJsonBytes(mockElectionRegistration))
        )
    }

}
