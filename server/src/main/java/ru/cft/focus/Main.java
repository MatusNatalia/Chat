package ru.cft.focus;

import ru.cft.focus.server.Server;
import ru.cft.focus.server.ServerException;
import ru.cft.focus.server.util.ServerConfigParser;
import ru.cft.focus.server.util.ServerConfigParserException;

public class Main {
    public static void main(String[] args) {
        try {
            ServerConfigParser serverConfigParser = new ServerConfigParser();
            Server server = new Server(serverConfigParser.getServerPort(),
                    serverConfigParser.getMaxConnectionsNumber());
            server.start();
        } catch (ServerConfigParserException e) {
            System.out.println(e.getMessage());
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
