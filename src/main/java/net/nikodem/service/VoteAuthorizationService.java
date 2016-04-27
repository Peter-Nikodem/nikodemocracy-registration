package net.nikodem.service;

import net.nikodem.model.entity.ElectionEntity;
import net.nikodem.model.entity.VoteAuthorizationEntity;
import net.nikodem.model.exception.NikodemocracyRequestException;
import net.nikodem.model.json.VoteAuthorizationRequest;
import net.nikodem.model.json.VoteAuthorizationResponse;
import net.nikodem.repository.VoteAuthorizationRepository;
import net.nikodem.repository.VoterRepository;
import net.nikodem.service.validation.VoteAuthorizationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Peter Nikodem
 */
@Service
public class VoteAuthorizationService {
    private VoteAuthorizationRepository voteAuthorizationRepository;
    private VoterRepository voterRepository;
    private VoteAuthorizationValidator voteAuthorizationValidator;

    public VoteAuthorizationResponse authorize(VoteAuthorizationRequest request) throws NikodemocracyRequestException {
        voteAuthorizationValidator.validate(request);
        return findVoteAuthorizationResponse(request);
    }

    private VoteAuthorizationResponse findVoteAuthorizationResponse(VoteAuthorizationRequest request) {
        return voteAuthorizationRepository.findByVoterUsernameAndElectionElectionId(request.getUsername(), request.getElectionId()).get().toVoteAuthorizationResponse();
    }

    protected void createAuthorizations(ElectionEntity electionEntity, List<String> eligibleVoterUsernames) {
        VoterKeyGenerator generator = new VoterKeyGenerator();
        List<VoteAuthorizationEntity> authorizations = eligibleVoterUsernames.stream()
                .map(voterRepository::findByUsername)
                .map(Optional::get)
                .map(voterEntity -> new VoteAuthorizationEntity(generator.generateNextRandomVoterKey(), voterEntity, electionEntity))
                .collect(Collectors.toList());
        voteAuthorizationRepository.save(authorizations);
    }

    @Autowired
    public void setVoteAuthorizationRepository(VoteAuthorizationRepository voteAuthorizationRepository) {
        this.voteAuthorizationRepository = voteAuthorizationRepository;
    }

    @Autowired
    public void setVoterRepository(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @Autowired
    public void setVoteAuthorizationValidator(VoteAuthorizationValidator voteAuthorizationValidator) {
        this.voteAuthorizationValidator = voteAuthorizationValidator;
    }
}
