package net.nikodem.model.exception;

/**
 * @author Peter Nikodem
 */
public final class EmptyPasswordException extends VoterRegistrationException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Password must not be empty.";
    }
}
