package net.nikodem.model.entity;

import net.nikodem.model.json.VoteAuthorizationResponse;

import javax.persistence.*;

/**
 * @TODO rename to permission
 * @author Peter Nikodem
 */
@Entity(name = "VoteAuthorization")
public class VoteAuthorizationEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String voterKey;

    @ManyToOne
    private VoterEntity voter;

    @ManyToOne
    private ElectionEntity election;

    public VoteAuthorizationEntity(String voterKey, VoterEntity voter, ElectionEntity election) {
        this.voterKey = voterKey;
        this.voter = voter;
        this.election = election;
    }

    public VoteAuthorizationEntity() {
    }

    public String getVoterKey() {
        return voterKey;
    }

    public void setVoterKey(String voterKey) {
        this.voterKey = voterKey;
    }

    public VoterEntity getVoter() {
        return voter;
    }

    public void setVoter(VoterEntity voter) {
        this.voter = voter;
    }

    public ElectionEntity getElection() {
        return election;
    }

    public void setElection(ElectionEntity election) {
        this.election = election;
    }

    public VoteAuthorizationResponse toVoteAuthorizationResponse() {
        return new VoteAuthorizationResponse(voter.getUsername(), election.getElectionId(), voterKey);
    }
}
