package net.nikodem.model.exception;

/**
 * @author Peter Nikodem
 */
public final class RepeatedPasswordDoesNotMatchException extends UserRegistrationException {

    @Override
    protected String getSpecifiedErrorMessage(){
        return "Password and repeated password must match.";
    }
}
