package net.nikodem.service;

import net.nikodem.model.entity.VoterEntity;
import net.nikodem.model.json.VoterRegistrationRequest;
import net.nikodem.repository.VoterRepository;
import net.nikodem.service.validation.VoterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoterRegistrationService {

    private VoterValidator voterValidator;
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
    public void setVoterValidator(VoterValidator voterValidator) {
        this.voterValidator = voterValidator;
    }

    @Autowired
    public void setVoterRepository(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

}
