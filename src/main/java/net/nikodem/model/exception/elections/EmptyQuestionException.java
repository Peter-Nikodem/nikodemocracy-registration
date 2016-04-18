package net.nikodem.model.exception.elections;

/**
 * @author Peter Nikodem
 */
public class EmptyQuestionException extends ElectionCreationException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "Question must not be empty.";
    }
}
