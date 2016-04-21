package net.nikodem.service;

import net.nikodem.model.entity.ElectionEntity;
import net.nikodem.model.entity.VoteAuthorizationEntity;
import net.nikodem.repository.VoteAuthorizationRepository;
import net.nikodem.repository.VoterRepository;
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

    public void createAuthorizations(ElectionEntity electionEntity, List<String> eligibleVoterUsernames) {
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
}
