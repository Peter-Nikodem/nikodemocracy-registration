package net.nikodem.service.validation;

import net.nikodem.model.exception.*;
import net.nikodem.model.json.ElectionCreationRequest;
import net.nikodem.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.nikodem.util.CollectionUtils.getDuplicates;
import static net.nikodem.util.ValidationPreconditions.hasLessThanThreeElements;
import static net.nikodem.util.ValidationPreconditions.hasLessThanTwoElements;
import static net.nikodem.util.ValidationPreconditions.isNullOrEmpty;

@Component
public class ElectionValidator {

    private VoterRepository voterRepository;

    public void validate(ElectionCreationRequest electionCreationRequest) throws NikodemocracyRequestException {
        if (isNullOrEmpty(electionCreationRequest.getElectionId())) {
            throw new EmptyElectionIdException();
        }
        if (isNullOrEmpty(electionCreationRequest.getQuestion())) {
            throw new EmptyQuestionException();
        }
        if (hasLessThanTwoElements(electionCreationRequest.getAnswers())) {
            throw new NotEnoughAnswersException();
        }
        if (hasLessThanThreeElements(electionCreationRequest.getInvitedVoters())) {
            throw new NotEnoughVotersException();
        }
        Set<String> duplicateAnswers = getDuplicates(electionCreationRequest.getAnswers());
        if (!duplicateAnswers.isEmpty()) {
            throw new DuplicatedAnswersException(duplicateAnswers);
        }
        Set<String> duplicateVoters = getDuplicates(electionCreationRequest.getInvitedVoters());
        if (!duplicateVoters.isEmpty()) {
            throw new DuplicatedVotersException(duplicateVoters);
        }
        Set<String> nonExistingVoters = getNonExistingVoters(electionCreationRequest.getInvitedVoters());
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
