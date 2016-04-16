package net.nikodem.model.exception;

/**
 * @author Peter Nikodem
 */
public final class EmptyUsernameException extends UserRegistrationException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Username must not be empty.";
    }
}
