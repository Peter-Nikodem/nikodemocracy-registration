package net.nikodem.model.exception.election;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public class NotEnoughVotersException extends NikodemocracyRequestException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be at least two invited voters.";
    }
}
