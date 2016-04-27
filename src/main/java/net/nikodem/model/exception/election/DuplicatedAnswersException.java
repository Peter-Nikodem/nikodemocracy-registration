package net.nikodem.model.exception.election;

import net.nikodem.model.exception.NikodemocracyRequestException;

import java.util.Collections;
import java.util.Set;

/**
 * @author Peter Nikodem
 */
public class DuplicatedAnswersException extends NikodemocracyRequestException {
    private final Set<String> duplicatedAnswers;

    public DuplicatedAnswersException(Set<String> duplicatedAnswers) {
        this.duplicatedAnswers = Collections.unmodifiableSet(duplicatedAnswers);
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be no duplicate answers. " + duplicatedAnswers;
    }
}
