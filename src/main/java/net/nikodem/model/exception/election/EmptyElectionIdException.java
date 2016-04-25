package net.nikodem.model.exception.election;

/**
 * @author Peter Nikodem
 */
public class EmptyElectionIdException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "ElectionId must not be empty.";
    }
}
