package ru.cft.focus.server.connection;

import ru.cft.focus.AcceptMessage;
import ru.cft.focus.Message;
import ru.cft.focus.NameMessage;
import ru.cft.focus.UserStateChangedMessage;
import ru.cft.focus.server.Server;

import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {
    private static final boolean ACCEPTED = true;
    private static final boolean DENIED = false;
    private static final boolean JOIN_CHAT = false;
    private static final boolean LEFT_CHAT = true;
    private static final String NAME_IS_TAKEN_MSG = "This name is already taken";

    private final Server server;
    private final Socket socket;
    private final ConnectionReader reader;
    private final ConnectionWriter writer;
    private String name;

    public Connection(Socket socket, Server server) {
        try {
            this.socket = socket;
            reader = new ConnectionReader(socket);
            writer = new ConnectionWriter(socket);
            new Thread(writer).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = reader.readMessage();
                switch (message.getType()) {
                    case SET_NAME_MSG -> {
                        name = ((NameMessage) message).getName();
                        if (server.isNameTaken(name)) {
                            sendMessage(new AcceptMessage(DENIED, NAME_IS_TAKEN_MSG, null));
                        } else {
                            sendMessage(new AcceptMessage(ACCEPTED, null, server.getAllUsers()));
                            server.addUser(name, this);
                            server.sendBroadcastMessage(new UserStateChangedMessage(JOIN_CHAT, name));
                        }
                    }
                    case TEXT_MSG -> {
                        server.sendBroadcastMessage(message);
                    }
                    default -> throw new UnsupportedOperationException();
                }
            } catch (IOException e) {
                if (name != null) {
                    server.removeUser(name);
                    server.sendBroadcastMessage(new UserStateChangedMessage(LEFT_CHAT, name));
                }
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
                writer.stop();
                break;
            }
        }
    }

    public void sendMessage(Message message) {
        writer.writeMessage(message);
    }

}
