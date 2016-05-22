package net.nikodem.service.validation;

import net.nikodem.model.dto.*;
import net.nikodem.model.exception.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import static net.nikodem.util.ValidationPreconditions.*;

@Component
public class VoterRegistrationValidator {

    private VoterRepository voterRepository;

    public void validate(VoterRegistrationRequest voterRegistrationRequest)
            throws EmptyUsernameException, EmptyPasswordException, RepeatedPasswordDoesNotMatchException {
        String username = voterRegistrationRequest.getUsername();
        String password = voterRegistrationRequest.getPassword();
        String repeatedPassword = voterRegistrationRequest.getRepeatedPassword();

        if (isNullOrEmpty(username)) {
            throw new EmptyUsernameException();
        }
        if (isNullOrEmpty(password)) {
            throw new EmptyPasswordException();
        }
        if (!password.equals(repeatedPassword)) {
            throw new RepeatedPasswordDoesNotMatchException();
        }
        if (usernameAlreadyExists(username)) {
            throw new UsernameAlreadyExistsException();
        }
    }

    private boolean usernameAlreadyExists(String username) {
        return voterRepository.existsByUsername(username);
    }


    @Autowired
    public void setVoterRepository(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }
}
