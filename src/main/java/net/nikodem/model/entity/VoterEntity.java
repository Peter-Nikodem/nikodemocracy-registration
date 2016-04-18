package net.nikodem.model.entity;

import net.nikodem.model.dto.VoterRegistrationRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Peter Nikodem
 */
@Entity(name = "Voter")
public class VoterEntity {

    public static VoterEntity createFromDto(VoterRegistrationRequest voterRegistration) {
        return new VoterEntity(voterRegistration.getUsername(),voterRegistration.getPassword());
    }

    public VoterEntity() {
    }

    public VoterEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue
    long id;

    @Column
    private String username;

    @Column
    private String password;
}
