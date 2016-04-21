package net.nikodem.model.exception.elections;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Peter Nikodem
 */
public class DuplicateAnswersException extends ElectionCreationException {
    private final Set<String> duplicatedAnswers;

    public DuplicateAnswersException(Set<String> duplicatedAnswers) {
        this.duplicatedAnswers = Collections.unmodifiableSet(duplicatedAnswers);
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be no duplicate answers. " + duplicatedAnswers;
    }
}
