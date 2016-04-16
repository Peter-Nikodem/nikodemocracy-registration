package net.nikodem.service

import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class VoterRegistrationServiceTest extends Specification {
        VoterRegistrationService registrationService

        def setup(){
            registrationService = new VoterRegistrationService()
        }

        def "Registering voter with empty username should throw exception"(){

        }

        def "Registering voter with empty password should throw exception"(){

        }

        def "Registering voter with with unmatching passwords should throw exception"(){

        }

        def "Registering voter with already existing username should throw exception"(){

        }

        def "Registering voter with valid information should pass without exceptions"(){

        }




}
