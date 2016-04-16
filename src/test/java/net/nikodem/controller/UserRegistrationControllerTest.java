package net.nikodem.controller;

import net.nikodem.TestUtils;
import net.nikodem.model.dto.UserRegistrationDetails;
import net.nikodem.model.exception.EmptyPasswordException;
import net.nikodem.model.exception.EmptyUsernameException;
import net.nikodem.model.exception.RepeatedPasswordDoesNotMatchException;
import net.nikodem.model.exception.UsernameAlreadyExistsException;
import net.nikodem.model.json.UserRegistration;
import net.nikodem.service.UserRegistrationService;
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
public class UserRegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRegistrationService userRegistrationServiceMock;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    private UserRegistration userRegistration = new UserRegistration("peter","password","password");

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).build();
    }

    @Test
    public void registerNewUser_ShouldReturnHttpCodeCreated_WhenValidationPasses() throws Exception {
        doNothing().when(userRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(userRegistration))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void registerNewUser_ShouldReturnError_WhenUsernameAlreadyExists() throws Exception {
        doThrow(UsernameAlreadyExistsException.class).when(userRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(userRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Entered username already exists.")));
    }

    @Test
    public void registerNewUser_ShouldReturnError_WhenPasswordsDoNotMatch() throws Exception {
        doThrow(RepeatedPasswordDoesNotMatchException.class).when(userRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(userRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Password and repeated password must match.")));
    }

    @Test
    public void registerNewUser_ShouldReturnError_WhenUsernameIsEmpty() throws Exception {
        doThrow(EmptyUsernameException.class).when(userRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(userRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Username must not be empty.")));
    }

    @Test
    public void registerNewUser_ShouldReturnError_WhenPasswordIsEmpty() throws Exception {
        doThrow(EmptyPasswordException.class).when(userRegistrationServiceMock).registerUser(any());
        mockMvc.perform(post("/users")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(userRegistration))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is("Password must not be empty.")));
    }

}
