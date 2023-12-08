package ru.cft.focus.server.util;

public class ServerConfigParserException extends Exception {

    private static final String ERROR_MSG = "Error while parsing config: ";
    private final String message;

    public ServerConfigParserException(String message) {
        this.message = ERROR_MSG + message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
