package net.nikodem.model.dto;

import java.util.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        VoteAuthorizationRequest that = (VoteAuthorizationRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(electionId, that.electionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, electionId);
    }
}
