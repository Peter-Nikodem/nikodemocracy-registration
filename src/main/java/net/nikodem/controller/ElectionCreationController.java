package net.nikodem.controller;

import net.nikodem.model.exception.*;
import net.nikodem.model.json.ElectionCreationRequest;
import net.nikodem.model.json.ErrorMessage;
import net.nikodem.service.ElectionCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ElectionCreationController {

    @Autowired
    private ElectionCreationService electionCreationService;

    @RequestMapping(value ="/elections",method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> createElection(@RequestBody ElectionCreationRequest electionCreationRequest){
        try {
            electionCreationService.createElection(electionCreationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NikodemocracyRequestException requestException){
            return new ResponseEntity<>(requestException.getErrorMessageJson(), HttpStatus.BAD_REQUEST);
        } catch (NikodemocracyServerException serverException){
            return new ResponseEntity<ErrorMessage>(serverException.getErrorMessageJson(),HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
