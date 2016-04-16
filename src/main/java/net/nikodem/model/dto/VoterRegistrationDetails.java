package net.nikodem.model.dto;

import net.nikodem.model.json.VoterRegistration;

/**
 * @author Peter Nikodem
 */
public class VoterRegistrationDetails {
    private final String username;
    private final String password;
    private final String repeatedPassword;

    public VoterRegistrationDetails(String username, String password, String repeatedPassword) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public static VoterRegistrationDetails createFromJson(VoterRegistration voterRegistration) {
        return new VoterRegistrationDetails(voterRegistration.getUsername(), voterRegistration.getPassword(), voterRegistration.getRepeatedPassword());
    }
}
