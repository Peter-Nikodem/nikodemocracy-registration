package net.nikodem.service;

import net.nikodem.model.dto.UserRegistrationDetails;
import net.nikodem.model.exception.UsernameAlreadyExistsException;
import org.springframework.stereotype.Service;

/**
 * @author Peter Nikodem
 */
@Service
public class UserRegistrationService {
    public void registerUser(UserRegistrationDetails user) {
        throw new UsernameAlreadyExistsException();
    }
}
