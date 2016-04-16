package net.nikodem.controller;

import net.nikodem.model.dto.UserRegistrationDetails;
import net.nikodem.model.exception.UserRegistrationException;
import net.nikodem.model.json.ErrorMessage;
import net.nikodem.model.json.UserRegistration;
import net.nikodem.service.UserRegistrationService;
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
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<ErrorMessage> registerNewUser(@RequestBody UserRegistration userRegistration, HttpServletResponse response){
        try {
            userRegistrationService.registerUser(UserRegistrationDetails.createFromJson(userRegistration));
            return new ResponseEntity<ErrorMessage>(HttpStatus.CREATED);
        } catch (UserRegistrationException ex){
            return new ResponseEntity<ErrorMessage>(ex.getErrorMessageJson(),HttpStatus.BAD_REQUEST);
        }
    }

}
