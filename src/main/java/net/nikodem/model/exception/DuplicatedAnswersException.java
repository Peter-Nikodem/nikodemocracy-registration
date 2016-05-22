package net.nikodem.model.exception;

import java.util.*;

public final class DuplicatedAnswersException extends NikodemocracyRequestException {

    private final Set<String> duplicatedAnswers;

    public DuplicatedAnswersException(Set<String> duplicatedAnswers) {
        this.duplicatedAnswers = Collections.unmodifiableSet(duplicatedAnswers);
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "There must be no duplicate answers. " + duplicatedAnswers;
    }
}
