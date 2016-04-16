package net.nikodem.model.json;

import javax.validation.constraints.NotNull;

/**
 * @author Peter Nikodem
 */
public class UserRegistration {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String repeatedPassword;

    public UserRegistration(String username, String password, String repeatedPassword) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public UserRegistration() {
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
}
