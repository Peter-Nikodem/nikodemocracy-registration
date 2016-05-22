package net.nikodem.service.validation;

import net.nikodem.model.dto.*;
import net.nikodem.model.entity.*;
import net.nikodem.model.exception.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

import java.util.*;

import static net.nikodem.util.ValidationPreconditions.*;

@Component
public class VoteAuthorizationValidator {

    private VoteAuthorizationRepository voteAuthorizationRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void validate(VoteAuthorizationRequest request) {
        if (isNullOrEmpty(request.getUsername())) {
            throw new EmptyUsernameException();
        }
        if (isNullOrEmpty(request.getPassword())) {
            throw new EmptyPasswordException();
        }
        if (isNullOrEmpty(request.getElectionId())) {
            throw new EmptyElectionIdException();
        }
        if (voterOrElectionDoesNotExistOrPasswordIsNotValid(request.getUsername(), request.getPassword(), request.getElectionId())) {
            throw new UnauthorizedVoterException();
        }
    }

    private boolean voterOrElectionDoesNotExistOrPasswordIsNotValid(String username, String rawEnteredPassword, String electionId) {
        Optional<VoteAuthorizationEntity> authorization = tryToFindAuthorization(username, electionId);
        if (authorization.isPresent()) {
            String storedEncodedPassword = authorization.get()
                    .getVoter()
                    .getPassword();
            return passwordIsNotValid(rawEnteredPassword, storedEncodedPassword);
        }
        return true;
    }

    private Optional<VoteAuthorizationEntity> tryToFindAuthorization(String username, String electionId) {
        return voteAuthorizationRepository.findByVoterUsernameAndElectionElectionId(username, electionId);
    }

    private boolean passwordIsNotValid(String rawEnteredPassword, String storedEncodedPassword) {
        return !passwordEncoder.matches(rawEnteredPassword, storedEncodedPassword);
    }

    @Autowired
    public void setVoteAuthorizationRepository(VoteAuthorizationRepository voteAuthorizationRepository) {
        this.voteAuthorizationRepository = voteAuthorizationRepository;
    }

    protected void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
