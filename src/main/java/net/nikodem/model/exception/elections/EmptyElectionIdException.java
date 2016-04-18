package net.nikodem.model.exception.elections;

/**
 * @author Peter Nikodem
 */
public class EmptyElectionIdException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "ElectionId must not be empty.";
    }
}
