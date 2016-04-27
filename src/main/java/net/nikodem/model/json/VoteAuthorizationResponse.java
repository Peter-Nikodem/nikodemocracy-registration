package net.nikodem.model.json;

/**
 * @author Peter Nikodem
 */
public class VoteAuthorizationResponse extends AbstractResponse {
    private String username;
    private String electionId;
    private String voterKey;

    public VoteAuthorizationResponse(String username, String electionId, String voterKey) {
        this.username = username;
        this.electionId = electionId;
        this.voterKey = voterKey;
    }

    public VoteAuthorizationResponse() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public String getVoterKey() {
        return voterKey;
    }

    public void setVoterKey(String voterKey) {
        this.voterKey = voterKey;
    }
}
