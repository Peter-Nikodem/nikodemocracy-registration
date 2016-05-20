package net.nikodem.controller;

import net.nikodem.model.exception.NikodemocracyRequestException;
import net.nikodem.model.json.AbstractResponse;
import net.nikodem.model.json.VoteAuthorizationRequest;
import net.nikodem.model.json.VoteAuthorizationResponse;
import net.nikodem.service.VoteAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthorizationRetrievalController {

    @Autowired
    private VoteAuthorizationService voteAuthorizationService;

    @RequestMapping(value="/authorizations",method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> retrieveAuthorization(@RequestBody VoteAuthorizationRequest request){
        try {
            VoteAuthorizationResponse authorizationResponse = voteAuthorizationService.authorize(request);
            return new ResponseEntity<>(authorizationResponse, HttpStatus.OK);
        } catch (NikodemocracyRequestException ex){
            return new ResponseEntity<>(ex.getErrorMessageJson(), HttpStatus.BAD_REQUEST);
        }
    }


}
