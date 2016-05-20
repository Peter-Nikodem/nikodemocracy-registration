package net.nikodem.model.exception;

public final class UnauthorizedVoterException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Unauthorized access.";
    }
}
