package net.nikodem.model.exception;

public class UnauthorizedVoterException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Unauthorized access.";
    }
}
