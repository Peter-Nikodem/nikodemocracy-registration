package net.nikodem.model.exception;

import java.util.Set;

public final class VoterDoesNotExistException extends NikodemocracyRequestException {

    private final Set<String> invalidUserNames;

    public VoterDoesNotExistException(Set<String> invalidUserNames) {
        this.invalidUserNames = invalidUserNames;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Voters with usernames " + invalidUserNames + " not found.";
    }
}
