package net.nikodem.model.exception;

import java.util.*;

public final class DuplicatedVotersException extends NikodemocracyRequestException {

    private final Set<String> duplicatedVoters;

    public DuplicatedVotersException(Set<String> duplicatedVoters) {
        this.duplicatedVoters = Collections.unmodifiableSet(duplicatedVoters);
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be no duplicated voters. " + duplicatedVoters;
    }
}
