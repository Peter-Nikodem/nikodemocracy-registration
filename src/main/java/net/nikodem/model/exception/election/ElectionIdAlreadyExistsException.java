package net.nikodem.model.exception.election;

import net.nikodem.model.exception.NikodemocracyRequestException;

/**
 * @author Peter Nikodem
 */
public class ElectionIdAlreadyExistsException extends NikodemocracyRequestException {
    @Override
    protected String getSpecifiedErrorMessage() {
        return "Entered electionId already exists.";
    }


}
