package net.nikodem.model.exception.voters;

/**
 * @author Peter Nikodem
 */
public final class RepeatedPasswordDoesNotMatchException extends VoterRegistrationException {

    @Override
    protected String getSpecifiedErrorMessage(){
        return "Password and repeated password must match.";
    }
}