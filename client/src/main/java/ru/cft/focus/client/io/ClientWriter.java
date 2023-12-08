package ru.cft.focus.client.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focus.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientWriter {

    private final OutputStream outputStream;
    private final ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

    public ClientWriter(Socket socket) throws IOException {
        outputStream = socket.getOutputStream();
    }

    public void send(Message message) throws IOException {
        objectMapper.writeValue(outputStream, message);
    }

}
