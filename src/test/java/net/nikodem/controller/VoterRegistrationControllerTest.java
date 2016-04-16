package net.nikodem.controller;

import net.nikodem.TestUtils;
import net.nikodem.model.exception.EmptyPasswordException;
import net.nikodem.model.exception.EmptyUsernameException;
import net.nikodem.model.exception.RepeatedPasswordDoesNotMatchException;
import net.nikodem.model.exception.UsernameAlreadyExistsException;
import net.nikodem.model.json.VoterRegistration;
import net.nikodem.service.VoterRegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Peter Nikodem
 */
public class VoterRegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VoterRegistrationService voterRegistrationServiceMock;

    @InjectMocks
    private VoterRegistrationController voterRegistrationController;

    private VoterRegistration voterRegistration = new VoterRegistration("peter","password","password");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(voterRegistrationController).build();
    }

    @Test
    public void registerVoter_ShouldReturnHttpCodeCreated_WhenValidationPasses() throws Exception {
        doNothing().when(voterRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(voterRegistration))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void registerVoter_ShouldReturnError_WhenUsernameAlreadyExists() throws Exception {
        doThrow(UsernameAlreadyExistsException.class).when(voterRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(voterRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Entered username already exists.")));
    }

    @Test
    public void registerVoter_ShouldReturnError_WhenPasswordsDoNotMatch() throws Exception {
        doThrow(RepeatedPasswordDoesNotMatchException.class).when(voterRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(voterRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Password and repeated password must match.")));
    }

    @Test
    public void registerVoter_ShouldReturnError_WhenUsernameIsEmpty() throws Exception {
        doThrow(EmptyUsernameException.class).when(voterRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(voterRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Username must not be empty.")));
    }

    @Test
    public void registerVoter_ShouldReturnError_WhenPasswordIsEmpty() throws Exception {
        doThrow(EmptyPasswordException.class).when(voterRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(voterRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Password must not be empty.")));
    }

}
