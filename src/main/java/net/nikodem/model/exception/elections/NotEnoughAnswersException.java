package net.nikodem.model.exception.elections;

/**
 * @author Peter Nikodem
 */
public class NotEnoughAnswersException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be at least two possible answers.";
    }
}
