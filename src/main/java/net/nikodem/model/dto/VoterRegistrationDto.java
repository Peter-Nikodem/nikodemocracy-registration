package net.nikodem.model.dto;

import net.nikodem.model.json.VoterRegistration;

/**
 * @author Peter Nikodem
 */
public class VoterRegistrationDto {
    private final String username;
    private final String password;
    private final String repeatedPassword;

    public VoterRegistrationDto(String username, String password, String repeatedPassword) {
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

    public static VoterRegistrationDto createFromJson(VoterRegistration voterRegistration) {
        return new VoterRegistrationDto(voterRegistration.getUsername(), voterRegistration.getPassword(), voterRegistration.getRepeatedPassword());
    }
}
