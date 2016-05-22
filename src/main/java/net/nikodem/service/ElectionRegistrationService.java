package net.nikodem.service;

import net.nikodem.model.dto.*;
import net.nikodem.model.entity.*;
import net.nikodem.model.exception.*;
import net.nikodem.repository.*;
import net.nikodem.service.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class ElectionRegistrationService {

    private ElectionRegistrationValidator electionValidator;
    private ElectionRepository electionRepository;
    private AnswerService answerService;
    private VoteAuthorizationService voteAuthorizationService;
    private ElectionTransferService electionTransferService;

    @Transactional
    public void registerElection(ElectionRegistrationRequest electionRegistrationRequest)
            throws NikodemocracyRequestException {
        validate(electionRegistrationRequest);
        List<String> generatedVoterKeys = saveElectionDataAndGetGeneratedVoterKeys(electionRegistrationRequest);
        sendElectionDetailsToTabulationAuthority(electionRegistrationRequest, generatedVoterKeys);
    }

    private void validate(ElectionRegistrationRequest electionRegistrationRequest) {
        electionValidator.validate(electionRegistrationRequest);
    }

    private List<String> saveElectionDataAndGetGeneratedVoterKeys(ElectionRegistrationRequest election) {
        ElectionEntity electionEntity = electionRepository.save(ElectionEntity.fromCreationRequest(election));
        answerService.saveAnswers(electionEntity, election.getAnswers());
        return voteAuthorizationService.createAndSaveAuthorizations(electionEntity, election.getInvitedVoters());
    }

    private void sendElectionDetailsToTabulationAuthority(ElectionRegistrationRequest request, List<String> voterKeys) {
        ElectionInformation electionInfo = new ElectionInformation(request.getElectionId(), request.getQuestion(),
                request.getAnswers(), voterKeys);
        electionTransferService.postElection(electionInfo);
    }

    @Autowired
    public void setElectionValidator(ElectionRegistrationValidator electionValidator) {
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
    public void setElectionTransferService(ElectionTransferService electionTransferService) {
        this.electionTransferService = electionTransferService;
    }
}
