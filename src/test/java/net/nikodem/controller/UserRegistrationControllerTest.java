package net.nikodem.controller;

import net.nikodem.service.UserRegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Peter Nikodem
 */
public class UserRegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRegistrationService userRegistrationServiceMock;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).build();
    }

    @Test
    public void registerNewUser_ShouldReturnHttpCodeCreated_WhenValidationPasses() throws Exception {

    }

    @Test
    public void registerNewUser_ShouldReturnError_WhenUsernameAlreadyExists() throws Exception {

    }

    @Test void registerNewUser_ShouldReturnError_WhenPasswordsDoNotMatch() throws Exception {

    }

    @Test void registerNewUser_ShouldReturnError_WhenUsernameIsEmpty() throws Exception {

    }

    @Test void registerNewUser_ShouldReturnError_WhenPasswordIsEmpty() throws Exception {

    }

}
