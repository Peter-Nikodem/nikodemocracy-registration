package net.nikodem.controller;

import net.nikodem.model.exception.voter.VoterRegistrationException;
import net.nikodem.model.json.ErrorMessage;
import net.nikodem.model.json.VoterRegistration;
import net.nikodem.service.VoterRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Peter Nikodem
 */
@Controller
public class VoterRegistrationController {

    @Autowired
    private VoterRegistrationService voterRegistrationService;

    @RequestMapping(value = "/voters", method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> registerVoter(@RequestBody VoterRegistration voterRegistration){
        try {
            voterRegistrationService.registerVoter(voterRegistration);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (VoterRegistrationException ex){
            return new ResponseEntity<>(ex.getErrorMessageJson(),HttpStatus.BAD_REQUEST);
        }
    }

}
