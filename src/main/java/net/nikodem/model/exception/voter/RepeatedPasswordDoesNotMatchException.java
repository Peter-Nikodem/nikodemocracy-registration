package net.nikodem.model.exception.voter;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public final class RepeatedPasswordDoesNotMatchException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage(){
        return "Password and repeated password must match.";
    }
}
