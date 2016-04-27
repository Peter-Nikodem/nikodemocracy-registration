package net.nikodem.model.entity;

import net.nikodem.model.json.VoterRegistrationRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Peter Nikodem
 */
@Entity(name = "Voter")
public class VoterEntity {


    @Id
    @GeneratedValue
    long id;

    @Column
    private String username;

    @Column
    private String password;

    public static VoterEntity createFromDto(VoterRegistrationRequest voterRegistrationRequest) {
        return new VoterEntity(voterRegistrationRequest.getUsername(), voterRegistrationRequest.getPassword());
    }

    public VoterEntity() {
    }

    public VoterEntity(String username, String password) {
        this.username = username;
        this.password = password;
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
}
