package ru.cft.focus.client;

import ru.cft.focus.NameMessage;
import ru.cft.focus.TextMessage;
import ru.cft.focus.client.io.ClientReader;
import ru.cft.focus.client.io.ClientWriter;
import ru.cft.focus.client.listeners.ConnectListener;
import ru.cft.focus.client.listeners.ErrorListener;
import ru.cft.focus.client.listeners.MessageListener;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final String INVALID_PORT_MSG = "Invalid port. Port must be [0;65535]";
    private static final String UNKNOWN_HOST_MSG = "Unknown host";
    private static final String CONNECT_ERROR_MSG = "Can't connect to server";
    private static final String CLIENT_ERROR_MSG = "Client error";
    private static final String SERVER_ERROR_MSG = "Server error";

    private Socket socket;
    private ClientWriter writer;
    private ClientReader reader;
    private final List<MessageListener> messageListeners = new ArrayList<>();
    private final List<ConnectListener> connectListeners = new ArrayList<>();
    private final List<ErrorListener> errorListeners = new ArrayList<>();
    private String userName;
    Thread readerThread;

    public void connectToServer(String host, String port) {
        try {
            socket = new Socket(host, Integer.parseInt(port));
            reader = new ClientReader(socket, messageListeners);
            writer = new ClientWriter(socket);
            readerThread = new Thread(reader);
            readerThread.start();
            notifyConnectListeners();
        } catch (IllegalArgumentException e) {
            stop();
            notifyErrorListeners(INVALID_PORT_MSG);
        } catch (UnknownHostException e) {
            stop();
            notifyErrorListeners(UNKNOWN_HOST_MSG);
        } catch (IOException e) {
            stop();
            notifyErrorListeners(CONNECT_ERROR_MSG);
        } catch (Exception e) {
            stop();
            notifyErrorListeners(CLIENT_ERROR_MSG);
        }
    }

    public void sendMessage(String message) {
        try {
            writer.send(new TextMessage(userName, message));
        } catch (IOException e) {
            notifyErrorListeners(SERVER_ERROR_MSG);
        }
    }

    public void sendName(String name) {
        userName = name;
        try {
            writer.send(new NameMessage(name));
        } catch (IOException e) {
            notifyErrorListeners(SERVER_ERROR_MSG);
        }
    }

    public void addMessageListener(MessageListener messageListener) {
        synchronized (messageListeners) {
            messageListeners.add(messageListener);
        }
    }

    public void addConnectListener(ConnectListener connectListener) {
        connectListeners.add(connectListener);
    }

    public void addErrorListener(ErrorListener errorListener) {
        synchronized (errorListeners) {
            errorListeners.add(errorListener);
        }
    }

    public void notifyConnectListeners() {
        connectListeners.forEach(ConnectListener::handleConnect);
    }

    public void notifyErrorListeners(String errorMessage) {
        synchronized (errorListeners) {
            errorListeners.forEach(errorListener -> errorListener.handleError(errorMessage));
        }
    }

    public void stop() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
        if (reader != null) {
            reader.stop();
        }
    }
}
