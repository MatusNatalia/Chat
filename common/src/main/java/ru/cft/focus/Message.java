package ru.cft.focus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NameMessage.class),
        @JsonSubTypes.Type(value = TextMessage.class),
        @JsonSubTypes.Type(value = UserStateChangedMessage.class),
        @JsonSubTypes.Type(value = AcceptMessage.class)
}
)
public class Message {

    private MessageType type;
    private final Date date = new Date();

    public Message(MessageType type) {
        this.type = type;
    }

    public Message() {

    }

    public MessageType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }
}
