package net.nikodem.model.exception.elections;

/**
 * @author Peter Nikodem
 */
public class NotEnoughVotersException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be at least two invited voters.";
    }
}
