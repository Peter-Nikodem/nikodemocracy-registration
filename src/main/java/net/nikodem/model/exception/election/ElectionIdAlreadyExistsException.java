package net.nikodem.model.exception.election;

/**
 * @author Peter Nikodem
 */
public class ElectionIdAlreadyExistsException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered electionId already exists.";
    }


}
