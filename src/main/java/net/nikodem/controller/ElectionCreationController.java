package net.nikodem.controller;

import net.nikodem.model.exception.election.ElectionCreationException;
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

/**
 * @author Peter Nikodem
 */
@Controller
public class ElectionCreationController {

    @Autowired
    private ElectionCreationService electionCreationService;

    @RequestMapping(value ="/elections",method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> createElection(@RequestBody ElectionCreationRequest electionCreationRequest){
        try {
            electionCreationService.createElection(electionCreationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ElectionCreationException ex){
            return new ResponseEntity<>(ex.getErrorMessageJson(), HttpStatus.BAD_REQUEST);
        }
    }

}
