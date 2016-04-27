package net.nikodem.service;

import net.nikodem.model.entity.VoterEntity;
import net.nikodem.model.exception.voter.EmptyPasswordException;
import net.nikodem.model.exception.voter.EmptyUsernameException;
import net.nikodem.model.exception.voter.RepeatedPasswordDoesNotMatchException;
import net.nikodem.model.exception.voter.UsernameAlreadyExistsException;
import net.nikodem.model.json.VoterRegistrationRequest;
import net.nikodem.repository.VoterRepository;
import net.nikodem.service.validation.VoterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.nikodem.util.ValidationPreconditions.isNullOrEmpty;

/**
 * @author Peter Nikodem
 */
@Service
public class VoterRegistrationService {

    private VoterValidator voterValidator;
    private VoterRepository voterRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public void setVoterRepository(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Autowired
    public void setVoterValidator(VoterValidator voterValidator) {
        this.voterValidator = voterValidator;
    }

    @Transactional
    public void registerVoter(VoterRegistrationRequest voterRegistrationRequest) {
        voterValidator.validate(voterRegistrationRequest);
        encryptPasswordAndSaveVoter(voterRegistrationRequest);
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void encryptPasswordAndSaveVoter(VoterRegistrationRequest voterRegistrationRequest) {
        String username = voterRegistrationRequest.getUsername();
        String encryptedPassword = encryptPassword(voterRegistrationRequest.getPassword());
        VoterEntity voter = new VoterEntity(username, encryptedPassword);
        voterRepository.save(voter);
    }

}
