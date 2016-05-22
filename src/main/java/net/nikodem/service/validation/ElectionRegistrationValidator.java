package net.nikodem.service.validation;

import net.nikodem.model.dto.*;
import net.nikodem.model.exception.*;
import net.nikodem.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

import static net.nikodem.util.CollectionUtils.*;
import static net.nikodem.util.ValidationPreconditions.*;

@Component
public class ElectionRegistrationValidator {

    private VoterRepository voterRepository;

    public void validate(ElectionRegistrationRequest electionRegistrationRequest) throws NikodemocracyRequestException {
        if (isNullOrEmpty(electionRegistrationRequest.getElectionId())) {
            throw new EmptyElectionIdException();
        }
        if (isNullOrEmpty(electionRegistrationRequest.getQuestion())) {
            throw new EmptyQuestionException();
        }
        if (hasLessThanTwoElements(electionRegistrationRequest.getAnswers())) {
            throw new NotEnoughAnswersException();
        }
        if (hasLessThanThreeElements(electionRegistrationRequest.getInvitedVoters())) {
            throw new NotEnoughVotersException();
        }
        Set<String> duplicateAnswers = getDuplicates(electionRegistrationRequest.getAnswers());
        if (!duplicateAnswers.isEmpty()) {
            throw new DuplicatedAnswersException(duplicateAnswers);
        }
        Set<String> duplicateVoters = getDuplicates(electionRegistrationRequest.getInvitedVoters());
        if (!duplicateVoters.isEmpty()) {
            throw new DuplicatedVotersException(duplicateVoters);
        }
        Set<String> nonExistingVoters = getNonExistingVoters(electionRegistrationRequest.getInvitedVoters());
        if (!nonExistingVoters.isEmpty()) {
            throw new VoterDoesNotExistException(nonExistingVoters);
        }
    }

    private Set<String> getNonExistingVoters(List<String> invitedVoters) {
        return invitedVoters.stream()
                .filter(this::voterDoesNotExist)
                .collect(Collectors.toSet());
    }

    private boolean voterDoesNotExist(String username) {
        return !voterRepository.existsByUsername(username);
    }

    @Autowired
    public void setVoterRepository(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

}
