package net.nikodem.model.exception.voter;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public final class EmptyPasswordException extends NikodemocracyRequestException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Password must not be empty.";
    }
}
