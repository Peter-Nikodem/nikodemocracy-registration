package net.nikodem.model.exception;

/**
 * @author Peter Nikodem
 */
public final class UsernameAlreadyExistsException extends UserRegistrationException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered username already exists.";
    }
}
