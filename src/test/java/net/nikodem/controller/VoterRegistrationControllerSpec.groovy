package net.nikodem.controller

import net.nikodem.TestUtils
import net.nikodem.model.exception.voters.EmptyPasswordException
import net.nikodem.model.exception.voters.EmptyUsernameException
import net.nikodem.model.exception.voters.RepeatedPasswordDoesNotMatchException
import net.nikodem.model.exception.voters.UsernameAlreadyExistsException
import net.nikodem.model.json.VoterRegistration
import net.nikodem.service.VoterRegistrationService
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author Peter Nikodem 
 */
class VoterRegistrationControllerSpec extends Specification {

    MockMvc mockMvc;

    @Mock
    VoterRegistrationService voterRegistrationServiceMock

    @InjectMocks
    VoterRegistrationController voterRegistrationController

    VoterRegistration mockVoterRegistration = new VoterRegistration("Not", "important", "here");


    def setup() {
        MockitoAnnotations.initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(voterRegistrationController).build()
    }

    def "Registering voter when details are valid should return HTTP code CREATED"() {
        when:
        doNothing().when(voterRegistrationServiceMock).registerVoter(any());
        then:
        mockMvc.perform(post("/voters")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(mockVoterRegistration))
        )
                .andExpect(status().isCreated());
    }

    def "Registering voter when username already exists should return error"() {
        when:
        doThrow(UsernameAlreadyExistsException).when(voterRegistrationServiceMock).registerVoter(any());
        then:
        performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs('Entered username already exists.')
    }

    def "Registering voter when passwords do not match should return error"() {
        when:
        doThrow(RepeatedPasswordDoesNotMatchException).when(voterRegistrationServiceMock).registerVoter(any())
        then:
        performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs('Password and repeated password must match.')
    }

    def "Registering voter when username is empty should return error"() {
        when:
        doThrow(EmptyUsernameException).when(voterRegistrationServiceMock).registerVoter(any())
        then:
        performBadRegistrationRequestAndVerifyThatReturnedErrorMessageIs('Username must not be empty.')

    }

    def "Registering voter when password is empty should return error"() {
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
