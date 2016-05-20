package net.nikodem.model.json;

import java.util.List;

public class ElectionCreationRequest {

    private String electionId;
    private String question;
    private List<String> answers;
    private List<String> invitedVoters;

    public ElectionCreationRequest() {
    }

    public ElectionCreationRequest(String electionId, String question, List<String> answers, List<String>
            invitedVoters) {
        this.electionId = electionId;
        this.question = question;
        this.answers = answers;
        this.invitedVoters = invitedVoters;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getInvitedVoters() {
        return invitedVoters;
    }

    public void setInvitedVoters(List<String> invitedVoters) {
        this.invitedVoters = invitedVoters;
    }

    @Override
    public String toString() {
        return "ElectionCreation{" +
                "electionId='" + electionId + '\'' +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                ", invitedVoters=" + invitedVoters +
                '}';
    }
}
