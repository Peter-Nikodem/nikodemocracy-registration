package net.nikodem.model.dto;

import net.nikodem.model.json.UserRegistration;

/**
 * @author Peter Nikodem
 */
public class UserRegistrationDetails {
    private final String username;
    private final String password;
    private final String repeatedPassword;

    public UserRegistrationDetails(String username, String password, String repeatedPassword) {
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

    public static UserRegistrationDetails createFromJson(UserRegistration userRegistration) {
        return new UserRegistrationDetails(userRegistration.getUsername(),userRegistration.getPassword(),userRegistration.getRepeatedPassword());
    }
}
