package net.nikodem.model.exception.voter;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public final class UsernameAlreadyExistsException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered username already exists.";
    }
}
