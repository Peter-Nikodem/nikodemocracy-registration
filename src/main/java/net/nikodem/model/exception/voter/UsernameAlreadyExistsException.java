package net.nikodem.model.exception.voter;

/**
 * @author Peter Nikodem
 */
public final class UsernameAlreadyExistsException extends VoterRegistrationException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered username already exists.";
    }
}
