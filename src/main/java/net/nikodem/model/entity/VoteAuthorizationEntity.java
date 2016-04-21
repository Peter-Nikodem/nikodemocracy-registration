package net.nikodem.model.entity;

import javax.persistence.*;

/**
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
    private ElectionEntity electionEntity;

    public VoteAuthorizationEntity(String voterKey, VoterEntity voter, ElectionEntity electionEntity) {
        this.voterKey = voterKey;
        this.voter = voter;
        this.electionEntity = electionEntity;
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

    public ElectionEntity getElectionEntity() {
        return electionEntity;
    }

    public void setElectionEntity(ElectionEntity electionEntity) {
        this.electionEntity = electionEntity;
    }
}
