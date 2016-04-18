package net.nikodem.controller

import net.nikodem.service.ElectionCreationService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class ElectionCreationControllerSpec extends Specification {

    MockMvc mockMvc

    @Mock
    ElectionCreationService electionCreationService

    @InjectMocks
    ElectionCreationController electionCreationController

    def setup(){
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(electionCreationController).build()
    }

    def "Creating election when details are valid should return HTTP code CREATED"(){

    }

    def "Creating election when electionId already exists should return error"(){

    }

    def "Creating election when electionId is empty should return error"(){

    }

    def "Creating election when question is empty should return error"(){

    }

    def "Creating election when there aren't at least two answers should return error"(){

    }

    def "Creating election when there aren't at least two voters should return error"(){

    }

    def "Creating election when any of the voters does not exist should return error"(){

    }

}
