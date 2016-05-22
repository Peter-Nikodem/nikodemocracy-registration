package net.nikodem.controller;

import net.nikodem.model.dto.*;
import net.nikodem.model.exception.*;
import net.nikodem.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class VoteAuthorizationRetrievalController {

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
