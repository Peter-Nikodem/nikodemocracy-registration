package net.nikodem.model.exception.election;

/**
 * @author Peter Nikodem
 */
public class NotEnoughAnswersException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be at least two possible answers.";
    }
}
