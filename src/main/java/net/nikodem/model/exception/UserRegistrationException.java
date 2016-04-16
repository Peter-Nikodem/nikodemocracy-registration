package net.nikodem.model.exception;

import net.nikodem.model.json.ErrorMessage;

/**
 * @author Peter Nikodem
 */
public abstract class UserRegistrationException extends IllegalArgumentException {

    abstract protected String getSpecifiedErrorMessage();

    public ErrorMessage getErrorMessageJson() {
        return new ErrorMessage(getSpecifiedErrorMessage());
    }
}
