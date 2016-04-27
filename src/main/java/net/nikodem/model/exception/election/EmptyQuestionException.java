package net.nikodem.model.exception.election;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public class EmptyQuestionException extends NikodemocracyRequestException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "Question must not be empty.";
    }
}
