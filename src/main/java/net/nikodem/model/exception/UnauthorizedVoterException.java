package net.nikodem.model.exception;

/**
 * @author Peter Nikodem
 */
public class UnauthorizedVoterException extends NikodemocracyRequestException {
    public UnauthorizedVoterException(String username) {
        this.username = username;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return null;
    }
}
