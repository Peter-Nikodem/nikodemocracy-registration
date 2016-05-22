package net.nikodem.model.entity;

import javax.persistence.*;

@Entity(name = "Voter")
public class VoterEntity {

    @Id
    @GeneratedValue
    long id;

    @Column
    private String username;

    @Column
    private String password;

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
