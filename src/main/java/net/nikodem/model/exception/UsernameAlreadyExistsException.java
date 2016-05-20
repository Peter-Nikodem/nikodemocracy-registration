package net.nikodem.model.exception;

public final class UsernameAlreadyExistsException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered username already exists.";
    }
}
