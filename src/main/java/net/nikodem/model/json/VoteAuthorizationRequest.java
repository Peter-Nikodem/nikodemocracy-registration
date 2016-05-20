package net.nikodem.model.json;

public class VoteAuthorizationRequest {
    private String username;
    private String password;
    private String electionId;

    public VoteAuthorizationRequest(String username, String password, String electionId) {
        this.username = username;
        this.password = password;
        this.electionId = electionId;
    }

    public VoteAuthorizationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }
}
