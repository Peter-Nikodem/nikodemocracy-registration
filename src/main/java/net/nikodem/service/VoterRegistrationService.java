package net.nikodem.service;

import net.nikodem.model.dto.*;
import net.nikodem.model.entity.*;
import net.nikodem.repository.*;
import net.nikodem.service.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
public class VoterRegistrationService {

    private VoterRegistrationValidator voterValidator;
    private VoterRepository voterRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @Autowired
    public void setVoterValidator(VoterRegistrationValidator voterValidator) {
        this.voterValidator = voterValidator;
    }

    @Autowired
    public void setVoterRepository(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

}
