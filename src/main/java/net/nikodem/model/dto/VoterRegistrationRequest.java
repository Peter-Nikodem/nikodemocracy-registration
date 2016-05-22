package net.nikodem.model.dto;

import java.util.*;

public class VoterRegistrationRequest {

    private String username;

    private String password;

    private String repeatedPassword;

    public VoterRegistrationRequest(String username, String password, String repeatedPassword) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public VoterRegistrationRequest() {
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

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    @Override
    public String toString() {
        return "UserRegistration{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repeatedPassword='" + repeatedPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        VoterRegistrationRequest that = (VoterRegistrationRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(repeatedPassword, that.repeatedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, repeatedPassword);
    }
}
