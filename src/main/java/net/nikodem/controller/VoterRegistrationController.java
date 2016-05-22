package net.nikodem.controller;

import net.nikodem.model.dto.*;
import net.nikodem.model.exception.*;
import net.nikodem.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class VoterRegistrationController {

    @Autowired
    private VoterRegistrationService voterRegistrationService;

    @RequestMapping(value = "/voters", method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> registerVoter(@RequestBody VoterRegistrationRequest voterRegistrationRequest){
        try {
            voterRegistrationService.registerVoter(voterRegistrationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NikodemocracyRequestException ex){
            return new ResponseEntity<>(ex.getErrorMessageJson(),HttpStatus.BAD_REQUEST);
        }
    }

}
