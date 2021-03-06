package net.nikodem.service;

import net.nikodem.model.dto.*;
import net.nikodem.model.entity.*;
import net.nikodem.model.exception.*;
import net.nikodem.repository.*;
import net.nikodem.service.crypto.*;
import net.nikodem.service.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

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
        return voteAuthorizationRepository.findByVoterUsernameAndElectionElectionId(request.getUsername(), request.getElectionId())
                .get()
                .toVoteAuthorizationResponse();
    }

    protected List<String> createAndSaveAuthorizations(ElectionEntity electionEntity, List<String>
            eligibleVoterUsernames) {
        List<VoteAuthorizationEntity> authorizations = getVoteAuthorizations(electionEntity, eligibleVoterUsernames);
        voteAuthorizationRepository.save(authorizations);
        return getVoterKeys(authorizations);
    }

    private List<VoteAuthorizationEntity> getVoteAuthorizations(ElectionEntity electionEntity, List<String> eligibleVoterUsernames) {
        VoterKeyGenerator generator = new VoterKeyGenerator();
        return eligibleVoterUsernames.stream()
                .map(voterRepository::findByUsername)
                .map(Optional::get)
                .map(voterEntity -> new VoteAuthorizationEntity(generator.generateNextRandomVoterKey(), voterEntity,
                        electionEntity))
                .collect(Collectors.toList());
    }

    private List<String> getVoterKeys(List<VoteAuthorizationEntity> authorizations) {
        return authorizations.stream()
                .map(VoteAuthorizationEntity::getVoterKey)
                .collect(Collectors.toList());
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
