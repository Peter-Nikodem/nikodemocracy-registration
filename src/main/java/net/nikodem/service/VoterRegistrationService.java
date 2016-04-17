package net.nikodem.service;

import net.nikodem.model.dto.VoterRegistrationDto;
import net.nikodem.model.entity.VoterEntity;
import net.nikodem.model.exception.EmptyPasswordException;
import net.nikodem.model.exception.EmptyUsernameException;
import net.nikodem.model.exception.RepeatedPasswordDoesNotMatchException;
import net.nikodem.model.exception.UsernameAlreadyExistsException;
import net.nikodem.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void registerVoter(VoterRegistrationDto voterRegistration) {
        validate(voterRegistration);
        ensureUniqueness(voterRegistration);
        encryptPasswordAndSaveVoter(voterRegistration);
    }

    private void validate(VoterRegistrationDto voterRegistration) throws
            EmptyUsernameException, EmptyPasswordException, RepeatedPasswordDoesNotMatchException {
        String username = voterRegistration.getUsername();
        String password = voterRegistration.getPassword();
        String repeatedPassword = voterRegistration.getRepeatedPassword();
        if (username == null || username.trim().isEmpty()) {
            throw new EmptyUsernameException();
        }
        if (password == null || password.trim().isEmpty()) {
            throw new EmptyPasswordException();
        }
        if (!password.equals(repeatedPassword)) {
            throw new RepeatedPasswordDoesNotMatchException();
        }

    }

    private void ensureUniqueness(VoterRegistrationDto voterRegistration) {
        if (voterRepository.existsByUsername(voterRegistration.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void encryptPasswordAndSaveVoter(VoterRegistrationDto voterRegistration) {
        String username = voterRegistration.getUsername();
        String encryptedPassword = encryptPassword(voterRegistration.getPassword());
        VoterEntity voter = new VoterEntity(username, encryptedPassword);
        voterRepository.save(voter);
    }

}
