package net.nikodem.model.exception;

public final class EmptyPasswordException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Password must not be empty.";
    }
}
