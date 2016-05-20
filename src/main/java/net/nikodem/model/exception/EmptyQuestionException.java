package net.nikodem.model.exception;

public final class EmptyQuestionException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Question must not be empty.";
    }
}
