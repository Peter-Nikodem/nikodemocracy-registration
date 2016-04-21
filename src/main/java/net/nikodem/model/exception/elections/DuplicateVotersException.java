package net.nikodem.model.exception.elections;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Peter Nikodem
 */
public class DuplicateVotersException extends ElectionCreationException {
    private final Set<String> duplicatedVoters;

    public DuplicateVotersException(Set<String> duplicatedVoters) {
        this.duplicatedVoters = Collections.unmodifiableSet(duplicatedVoters);
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be no duplicated voters. " + duplicatedVoters;
    }
}
