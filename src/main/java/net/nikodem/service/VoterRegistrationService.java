package net.nikodem.service;

import net.nikodem.model.entity.VoterEntity;
import net.nikodem.model.exception.voter.EmptyPasswordException;
import net.nikodem.model.exception.voter.EmptyUsernameException;
import net.nikodem.model.exception.voter.RepeatedPasswordDoesNotMatchException;
import net.nikodem.model.exception.voter.UsernameAlreadyExistsException;
import net.nikodem.model.json.VoterRegistration;
import net.nikodem.repository.VoterRepository;
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

    private VoterRepository voterRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void setVoterRepository(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Transactional
    public void registerVoter(VoterRegistration voterRegistration) {
        validate(voterRegistration);
        ensureUniqueness(voterRegistration);
        encryptPasswordAndSaveVoter(voterRegistration);
    }

    private void validate(VoterRegistration voterRegistration) throws
            EmptyUsernameException, EmptyPasswordException, RepeatedPasswordDoesNotMatchException {
        String username = voterRegistration.getUsername();
        String password = voterRegistration.getPassword();
        String repeatedPassword = voterRegistration.getRepeatedPassword();
        if (isNullOrEmpty(username)) {
            throw new EmptyUsernameException();
        }
        if (isNullOrEmpty(password)) {
            throw new EmptyPasswordException();
        }
        if (!password.equals(repeatedPassword)) {
            throw new RepeatedPasswordDoesNotMatchException();
        }

    }

    private void ensureUniqueness(VoterRegistration voterRegistration) {
        if (voterRepository.existsByUsername(voterRegistration.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void encryptPasswordAndSaveVoter(VoterRegistration voterRegistration) {
        String username = voterRegistration.getUsername();
        String encryptedPassword = encryptPassword(voterRegistration.getPassword());
        VoterEntity voter = new VoterEntity(username, encryptedPassword);
        voterRepository.save(voter);
    }

}
