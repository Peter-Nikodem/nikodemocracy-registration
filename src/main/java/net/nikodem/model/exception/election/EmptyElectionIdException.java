package net.nikodem.model.exception.election;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public class EmptyElectionIdException extends NikodemocracyRequestException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "ElectionId must not be empty.";
    }
}
