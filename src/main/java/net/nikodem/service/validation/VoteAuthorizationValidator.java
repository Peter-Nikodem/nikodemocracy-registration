package net.nikodem.service.validation;

import net.nikodem.model.entity.VoteAuthorizationEntity;
import net.nikodem.model.exception.UnauthorizedVoterException;
import net.nikodem.model.exception.election.EmptyElectionIdException;
import net.nikodem.model.exception.voter.EmptyPasswordException;
import net.nikodem.model.exception.voter.EmptyUsernameException;
import net.nikodem.model.json.VoteAuthorizationRequest;
import net.nikodem.repository.ElectionRepository;
import net.nikodem.repository.VoteAuthorizationRepository;
import net.nikodem.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static net.nikodem.util.ValidationPreconditions.isNullOrEmpty;

/**
 * @author Peter Nikodem
 */
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
            throw new UnauthorizedVoterException(request.getUsername());
        }
    }

    private boolean voterOrElectionDoesNotExistOrPasswordIsNotValid(String username, String rawEnteredPassword, String electionId) {
        Optional<VoteAuthorizationEntity> authorization = tryToFindAuthorization(username, electionId);
        if (authorization.isPresent()){
            String storedEncodedPassword = authorization.get().getVoter().getPassword();
            return passwordIsValid(rawEnteredPassword, storedEncodedPassword);
        }
        return false;
    };

    private Optional<VoteAuthorizationEntity> tryToFindAuthorization(String username, String electionId) {
        return voteAuthorizationRepository.findByVoterUsernameAndElectionElectionId(username,electionId);
    }

    private boolean passwordIsValid(String storedEncodedPassword, String rawEnteredPassword) {
        return passwordEncoder.matches(rawEnteredPassword, storedEncodedPassword);
    }

    @Autowired
    public void setVoteAuthorizationRepository(VoteAuthorizationRepository voteAuthorizationRepository) {
        this.voteAuthorizationRepository = voteAuthorizationRepository;
    }
}
