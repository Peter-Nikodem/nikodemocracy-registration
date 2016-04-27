package net.nikodem.model.exception.election;

import net.nikodem.model.exception.NikodemocracyRequestException;

import java.util.Set;

/**
 * @author Peter Nikodem
 */
public class VoterDoesNotExistException extends NikodemocracyRequestException {
    private final Set<String> invalidUserNames;

    public VoterDoesNotExistException(Set<String> invalidUserNames) {
        this.invalidUserNames = invalidUserNames;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Voters with usernames " + invalidUserNames + " not found.";
    }
}
