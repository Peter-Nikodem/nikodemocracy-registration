package net.nikodem.model.entity;

import net.nikodem.model.dto.*;

import javax.persistence.*;

@Entity(name = "Election")
public class ElectionEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String electionId;

    @Column
    private String question;

    @Column
    private boolean isFinished;

    public ElectionEntity() {
    }

    public ElectionEntity(String electionId, String question) {
        this.electionId = electionId;
        this.question = question;
        this.isFinished = false;
    }

    public static ElectionEntity fromCreationRequest(ElectionRegistrationRequest electionRegistrationRequest) {
        return new ElectionEntity(electionRegistrationRequest.getElectionId(), electionRegistrationRequest.getQuestion());
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }


}
