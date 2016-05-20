package net.nikodem.model.exception;

public class ElectionTransferFailedException extends NikodemocracyServerException {

    @Override
    protected String getSpecifiedErrorMessage() {
        return "Could not transfer the election details to the tabulation authority. Sorry!";
    }
}
