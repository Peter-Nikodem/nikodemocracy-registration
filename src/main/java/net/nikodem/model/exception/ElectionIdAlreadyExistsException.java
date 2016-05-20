package net.nikodem.model.exception;

public final class ElectionIdAlreadyExistsException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered electionId already exists.";
    }


}
