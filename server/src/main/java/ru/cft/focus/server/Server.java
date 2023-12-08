package ru.cft.focus.server;

import ru.cft.focus.Message;
import ru.cft.focus.server.connection.Connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    private final ServerSocket serverSocket;
    private final Map<String, Connection> connections;

    public Server(int port, int maxConnectionsNumber) throws ServerException {
        try {
            serverSocket = new ServerSocket(port, maxConnectionsNumber, InetAddress.getByName("localhost"));
            connections = new HashMap<>();
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
    }

    public void start() throws ServerException {
        while (true) {
            try {
                Connection connection = new Connection(serverSocket.accept(), this);
                new Thread(connection).start();
            } catch (IOException e) {
                throw new ServerException(e.getMessage());
            }
        }
    }

    public void sendBroadcastMessage(Message message) {
        synchronized (connections) {
            connections.values().forEach(connection -> connection.sendMessage(message));
        }
    }

    public void addUser(String user, Connection connection) {
        synchronized (connections) {
            connections.put(user, connection);
        }
    }

    public void removeUser(String user) {
        synchronized (connections) {
            connections.remove(user);
        }
    }

    public boolean isNameTaken(String name) {
        return connections.containsKey(name);
    }

    public List<String> getAllUsers() {
        return connections.keySet().stream().toList();
    }

}
