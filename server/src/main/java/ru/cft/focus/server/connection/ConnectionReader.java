package ru.cft.focus.server.connection;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focus.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ConnectionReader {

    private final InputStream inputStream;
    private final ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

    public ConnectionReader(Socket socket) throws IOException {
        inputStream = socket.getInputStream();
    }

    public Message readMessage() throws IOException {
        return objectMapper.readValue(inputStream, Message.class);
    }

}
