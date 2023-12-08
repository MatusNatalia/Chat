package ru.cft.focus.server;

public class ServerException extends Exception {

    private static final String ERROR_MSG = "Server error: ";
    private final String message;

    public ServerException(String message) {
        this.message = ERROR_MSG + message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
