package ru.cft.focus.server.connection;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focus.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionWriter implements Runnable {

    private final OutputStream outputStream;
    private final ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    private final ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);

    public ConnectionWriter(Socket socket) throws IOException {
        outputStream = socket.getOutputStream();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                writeMessageImpl(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (IOException ignored) {

            }
        }
    }

    public void writeMessage(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeMessageImpl(Message message) throws IOException {
        objectMapper.writeValue(outputStream, message);
    }

    public void stop() {
        Thread.currentThread().interrupt();
    }
}
