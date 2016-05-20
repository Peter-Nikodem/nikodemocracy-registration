package net.nikodem.model.exception;

public final class EmptyUsernameException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Username must not be empty.";
    }
}
