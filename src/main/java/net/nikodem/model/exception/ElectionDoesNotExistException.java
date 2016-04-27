package net.nikodem.model.exception;

/**
 * @author Peter Nikodem
 */
public class ElectionDoesNotExistException extends NikodemocracyRequestException {

    private final String electionId;

    public ElectionDoesNotExistException(String electionId) {
        this.electionId = electionId;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return null;
    }
}
