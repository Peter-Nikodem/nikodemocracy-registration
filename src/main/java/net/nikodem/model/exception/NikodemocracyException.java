package net.nikodem.model.exception;

import net.nikodem.model.json.*;

public abstract class NikodemocracyException extends RuntimeException {

    abstract protected String getSpecifiedErrorMessage();

    public ErrorMessage getErrorMessageJson() {
        return new ErrorMessage(getSpecifiedErrorMessage());
    }
}
