package net.nikodem.model.exception.election;

/**
 * @author Peter Nikodem
 */
public class NotEnoughVotersException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be at least two invited voters.";
    }
}
