package net.nikodem.controller;

import net.nikodem.model.dto.*;
import net.nikodem.model.exception.*;
import net.nikodem.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class ElectionRegistrationController {

    @Autowired
    private ElectionRegistrationService electionRegistrationService;

    @RequestMapping(value ="/elections",method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> createElection(@RequestBody ElectionRegistrationRequest electionRegistrationRequest){
        try {
            electionRegistrationService.registerElection(electionRegistrationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NikodemocracyRequestException requestException){
            return new ResponseEntity<>(requestException.getErrorMessageJson(), HttpStatus.BAD_REQUEST);
        } catch (NikodemocracyServerException serverException){
            return new ResponseEntity<>(serverException.getErrorMessageJson(),HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
