package ru.cft.focus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextMessage extends Message {

    @JsonProperty("text")
    private String text;
    @JsonProperty("name")
    private String userName;

    public TextMessage(String userName, String text) {
        super(MessageType.TEXT_MSG);
        this.userName = userName;
        this.text = text;
    }

    public TextMessage() {

    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }
}
