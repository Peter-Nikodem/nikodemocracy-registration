package net.nikodem.service

import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class UserRegistrationServiceTest extends Specification {
        UserRegistrationService registrationService

        def setup(){
            registrationService = new UserRegistrationService()
        }

        def "Registering user with empty username should throw exception"(){

        }

        def "Registering user with empty password should throw exception"(){

        }

        def "Registering user with with unmatching passwords should throw exception"(){

        }

        def "Registering user with already existing user should throw exception"(){

        }

        def "Registering user with valid information should pass without exceptions"(){

        }




}
