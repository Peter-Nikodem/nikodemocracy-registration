package net.nikodem.model.exception;

public class ElectionIdAlreadyExistsException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered electionId already exists.";
    }


}
