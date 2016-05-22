package net.nikodem.controller

import net.nikodem.TestUtils
import net.nikodem.model.dto.VoterRegistrationRequest
import net.nikodem.model.exception.EmptyPasswordException
import net.nikodem.model.exception.EmptyUsernameException
import net.nikodem.model.exception.RepeatedPasswordDoesNotMatchException
import net.nikodem.model.exception.UsernameAlreadyExistsException
import net.nikodem.service.VoterRegistrationService
import net.nikodem.testdata.ExampleVoter
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static org.mockito.Matchers.any
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class VoterRegistrationControllerSpec extends Specification {

    MockMvc mockMvc;

    @Mock
    VoterRegistrationService voterRegistrationServiceMock

    @InjectMocks
    VoterRegistrationController voterRegistrationController

    VoterRegistrationRequest mockVoterRegistration = ExampleVoter.BOB.voterRegistrationRequest;


    def setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(voterRegistrationController).build()
    }

    def "Registering voter when details are valid returns HTTP code CREATED"() {
        when:
        doNothing().when(voterRegistrationServiceMock).registerVoter(any());
        then:
        mockMvc.perform(post("/voters")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(mockVoterRegistration))
        )
                .andExpect(status().isCreated());
    }

    def "Registering voter when username already exists returns error"() {
        when:
        doThrow(UsernameAlreadyExistsException).when(voterRegistrationServiceMock).registerVoter(any());
        then:
        performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs('Entered username already exists.')
    }

    def "Registering voter when passwords do not match returns error"() {
        when:
        doThrow(RepeatedPasswordDoesNotMatchException).when(voterRegistrationServiceMock).registerVoter(any())
        then:
        performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs('Password and repeated password must match.')
    }

    def "Registering voter when username is empty returns error"() {
        when:
        doThrow(EmptyUsernameException).when(voterRegistrationServiceMock).registerVoter(any())
        then:
        performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs('Username must not be empty.')

    }

    def "Registering voter when password is empty returns error"() {
        when:
        doThrow(EmptyPasswordException).when(voterRegistrationServiceMock).registerVoter(any())
        then:
        performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs('Password must not be empty.')
    }

    def performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs(String errorMessage) {
        mockMvc.perform(post("/voters")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(mockVoterRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$', notNullValue()))
                .andExpect(jsonPath('$.errorMessage', is(errorMessage)));
    }

}
