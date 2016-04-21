package net.nikodem.model.exception.elections;

import java.util.Set;

/**
 * @author Peter Nikodem
 */
public class VoterDoesNotExistException extends ElectionCreationException{
    private final Set<String> invalidUserNames;

    public VoterDoesNotExistException(Set<String> invalidUserNames) {
        this.invalidUserNames = invalidUserNames;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Voters with usernames " + invalidUserNames + " not found.";
    }
}
