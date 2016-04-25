package net.nikodem.model.exception.voter;

/**
 * @author Peter Nikodem
 */
public final class EmptyUsernameException extends VoterRegistrationException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Username must not be empty.";
    }
}
