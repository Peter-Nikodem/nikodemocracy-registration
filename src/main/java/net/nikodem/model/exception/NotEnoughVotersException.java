package net.nikodem.model.exception;

public class NotEnoughVotersException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be at least three invited voters.";
    }
}
