package net.nikodem.model.exception;

public final class RepeatedPasswordDoesNotMatchException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage(){
        return "Password and repeated password must match.";
    }
}
