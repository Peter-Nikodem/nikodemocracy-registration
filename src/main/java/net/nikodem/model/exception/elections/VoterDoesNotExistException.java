package net.nikodem.model.exception.elections;

/**
 * @author Peter Nikodem
 */
public class VoterDoesNotExistException extends ElectionCreationException{
    private final String invalidUserName;

    public VoterDoesNotExistException(String invalidUserName) {
        this.invalidUserName = invalidUserName;
    }

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Voter with username \"" + invalidUserName + "\" not found.";
    }
}
