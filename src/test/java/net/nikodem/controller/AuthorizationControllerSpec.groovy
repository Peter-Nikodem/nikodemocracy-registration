package net.nikodem.controller

import net.nikodem.TestUtils
import net.nikodem.model.exception.UnauthorizedVoterException
import net.nikodem.model.exception.EmptyElectionIdException
import net.nikodem.model.exception.EmptyPasswordException
import net.nikodem.model.exception.EmptyUsernameException
import net.nikodem.model.json.VoteAuthorizationRequest
import net.nikodem.model.json.VoteAuthorizationResponse
import net.nikodem.service.VoteAuthorizationService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static org.mockito.Matchers.any
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class AuthorizationControllerSpec extends Specification {

    MockMvc mockMvc

    @Mock
    VoteAuthorizationService authorizationServiceMock

    @InjectMocks
    AuthorizationRetrievalController authorizationController

    VoteAuthorizationRequest request = new VoteAuthorizationRequest('peter','password','electionId')

    def setup() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(authorizationController).build()
    }

    def "Retrieving vote authorization with correct information returns authorization with appropriate voter key"() {
        when:
        when(authorizationServiceMock.authorize(any())).thenReturn(new VoteAuthorizationResponse('Peter','Password','1234567812345678'))
        then:
        mockMvc.perform(post("/authorizations")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(request))
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$', notNullValue()))
                .andExpect(jsonPath('$.username', is("Peter")))
                .andExpect(jsonPath('$.electionId',is("Password")))
                .andExpect(jsonPath('$.voterKey', is("1234567812345678")))
    }

    def "Retrieving vote authorization with empty username returns error"() {
        when:
        when(authorizationServiceMock.authorize(any())).thenThrow(EmptyUsernameException)
        then:
        performBadAuthorizationRequestAndVerifyThatReturnedErrorMessageIs('Username must not be empty.')
    }

    def "Retrieving vote authorization with empty password returns error"() {
        when:
        when(authorizationServiceMock.authorize(any())).thenThrow(EmptyPasswordException)
        then:
        performBadAuthorizationRequestAndVerifyThatReturnedErrorMessageIs('Password must not be empty.')
    }

    def "Retrieving vote authorization with empty electionId returns error"() {
        when:
        when(authorizationServiceMock.authorize(any())).thenThrow(EmptyElectionIdException)
        then:
        performBadAuthorizationRequestAndVerifyThatReturnedErrorMessageIs('ElectionId must not be empty.')
    }

    def "Retrieving vote authorization with usename and password do not match or when voter wasn't invited returns error"() {
        when:
        when(authorizationServiceMock.authorize(any())).thenThrow(UnauthorizedVoterException)
        then:
        performBadAuthorizationRequestAndVerifyThatReturnedErrorMessageIs('Unauthorized access.')
    }

    def performBadAuthorizationRequestAndVerifyThatReturnedErrorMessageIs(String errorMessage) {
        mockMvc.perform(post("/authorizations")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath('$', notNullValue()))
                .andExpect(jsonPath('$.errorMessage', is(errorMessage)));
    }




}
