package net.nikodem.model.dto;

public class ErrorMessage extends AbstractResponse {

    private String errorMessage;

    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
