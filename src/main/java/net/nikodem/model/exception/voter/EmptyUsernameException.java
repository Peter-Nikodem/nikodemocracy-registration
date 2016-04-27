package net.nikodem.model.exception.voter;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public final class EmptyUsernameException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Username must not be empty.";
    }
}
