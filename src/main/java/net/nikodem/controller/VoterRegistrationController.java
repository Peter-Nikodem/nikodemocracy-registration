package net.nikodem.controller;

import net.nikodem.model.dto.VoterRegistrationDto;
import net.nikodem.model.exception.VoterRegistrationException;
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

import javax.servlet.http.HttpServletResponse;

/**
 * @author Peter Nikodem
 */
@Controller
public class VoterRegistrationController {

    @Autowired
    private VoterRegistrationService voterRegistrationService;

    @RequestMapping(value = "/voters", method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> registerNewUser(@RequestBody VoterRegistration voterRegistration, HttpServletResponse response){
        try {
            voterRegistrationService.registerVoter(VoterRegistrationDto.createFromJson(voterRegistration));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (VoterRegistrationException ex){
            return new ResponseEntity<>(ex.getErrorMessageJson(),HttpStatus.BAD_REQUEST);
        }
    }

}
