package net.nikodem.service.validation;

import net.nikodem.model.exception.voter.EmptyPasswordException;
import net.nikodem.model.exception.voter.EmptyUsernameException;
import net.nikodem.model.exception.voter.RepeatedPasswordDoesNotMatchException;
import net.nikodem.model.exception.voter.UsernameAlreadyExistsException;
import net.nikodem.model.json.VoterRegistrationRequest;
import net.nikodem.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static net.nikodem.util.ValidationPreconditions.isNullOrEmpty;

/**
 * @author Peter Nikodem
 */
@Component
public class VoterValidator {

    private VoterRepository voterRepository;

    public void validate(VoterRegistrationRequest voterRegistrationRequest) throws
            EmptyUsernameException, EmptyPasswordException, RepeatedPasswordDoesNotMatchException {
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
