package net.nikodem.service;

import net.nikodem.model.entity.ElectionEntity;
import net.nikodem.model.exception.NikodemocracyRequestException;
import net.nikodem.model.json.*;
import net.nikodem.repository.ElectionRepository;
import net.nikodem.service.validation.ElectionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ElectionCreationService {

    private ElectionValidator electionValidator;
    private ElectionRepository electionRepository;
    private AnswerService answerService;
    private VoteAuthorizationService voteAuthorizationService;
    private ElectionTransferringService electionTransferringService;

    @Transactional
    public void createElection(ElectionCreationRequest electionCreationRequest) throws NikodemocracyRequestException {
        electionValidator.validate(electionCreationRequest);
        ElectionEntity electionEntity = electionRepository.save(ElectionEntity.fromCreationRequest
                (electionCreationRequest));
        answerService.saveAnswers(electionEntity, electionCreationRequest.getAnswers());
        List<String> generatedVoterKeys = voteAuthorizationService.createAndSaveAuthorizations(electionEntity,
                electionCreationRequest.getInvitedVoters());
        sendElectionDetailsToTabulationAuthority(electionCreationRequest, generatedVoterKeys);
    }

    private void sendElectionDetailsToTabulationAuthority(ElectionCreationRequest request, List<String> voterKeys) {
        ElectionInformation electionInfo = new ElectionInformation(request.getElectionId(), request.getQuestion(),
                request.getAnswers(), voterKeys);
        electionTransferringService.postElection(electionInfo);
    }

    @Autowired
    public void setElectionValidator(ElectionValidator electionValidator) {
        this.electionValidator = electionValidator;
    }

    @Autowired
    public void setElectionRepository(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setVoteAuthorizationService(VoteAuthorizationService voteAuthorizationService) {
        this.voteAuthorizationService = voteAuthorizationService;
    }

    @Autowired
    public void setElectionTransferringService(ElectionTransferringService electionTransferringService) {
        this.electionTransferringService = electionTransferringService;
    }
}
