package net.nikodem.model.exception;

public class NotEnoughAnswersException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be at least two possible answers.";
    }
}
