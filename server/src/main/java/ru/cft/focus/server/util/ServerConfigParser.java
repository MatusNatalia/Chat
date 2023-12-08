package ru.cft.focus.server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfigParser {
    private static final String CONFIG_PATH = "task6\\server\\src\\main\\resources\\server_config.properties";

    private static final String PORT_PROPERTY = "port";
    private static final String DEFAULT_PORT = "4040";
    private static final int MIN_PORT_NUMBER = 0;
    private static final int MAX_PORT_NUMBER = 65535;

    private static final String MAX_CONNECTIONS_PROPERTY = "max_connections";
    private static final String DEFAULT_MAX_CONNECTIONS = "100";

    private static final String INVALID_PORT_ERROR_MSG = " is not valid port number. Port must be in [" +
            MIN_PORT_NUMBER + ", " + MAX_PORT_NUMBER + "].";
    private static final String INVALID_MAX_CONNECTIONS_ERROR_MSG = " is not valid number of max connections. " +
            "Number must be positive";

    private final Properties properties = new Properties();

    public ServerConfigParser() throws ServerConfigParserException {
        try {
            properties.load(new FileInputStream(CONFIG_PATH));
        } catch (IOException e) {
            throw new ServerConfigParserException(e.getMessage());
        }
    }

    public int getServerPort() throws ServerConfigParserException {
        String portString = properties.getProperty(PORT_PROPERTY, DEFAULT_PORT);
        try {
            int port = Integer.parseInt(portString);
            if (port <= MIN_PORT_NUMBER || port >= MAX_PORT_NUMBER) {
                throw new ServerConfigParserException(port + INVALID_PORT_ERROR_MSG);
            }
            return port;
        } catch (NumberFormatException e) {
            throw new ServerConfigParserException(e.getMessage());
        }
    }

    public int getMaxConnectionsNumber() throws ServerConfigParserException {
        String maxConnectionsString = properties.getProperty(MAX_CONNECTIONS_PROPERTY, DEFAULT_MAX_CONNECTIONS);
        try {
            int maxConnections = Integer.parseInt(maxConnectionsString);
            if (maxConnections <= 0) {
                throw new ServerConfigParserException(INVALID_MAX_CONNECTIONS_ERROR_MSG);
            }
            return maxConnections;
        } catch (NumberFormatException e) {
            throw new ServerConfigParserException(e.getMessage());
        }
    }

}
