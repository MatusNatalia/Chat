package ru.cft.focus.client.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focus.Message;
import ru.cft.focus.client.listeners.MessageListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientReader implements Runnable {

    private final InputStream inputStream;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    private final List<MessageListener> messageListeners;
    private final AtomicBoolean stop = new AtomicBoolean(false);

    public ClientReader(Socket socket, List<MessageListener> messageListeners) throws IOException {
        inputStream = socket.getInputStream();
        this.messageListeners = messageListeners;
    }

    @Override
    public void run() {
        while (!stop.get()) {
            try {
                Message message = objectMapper.readValue(inputStream, Message.class);
                notifyMessageListeners(message);
            } catch (IOException e) {
                stop.set(true);
            }
        }
    }

    private void notifyMessageListeners(Message message) {
        synchronized (messageListeners) {
            messageListeners.forEach(listener -> listener.handleMessage(message));
        }
    }

    public void stop() {
        stop.set(true);
    }

}
