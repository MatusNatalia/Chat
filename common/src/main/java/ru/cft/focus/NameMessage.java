package ru.cft.focus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NameMessage extends Message {

    @JsonProperty("name")
    private String name;

    public NameMessage(String name) {
        super(MessageType.SET_NAME_MSG);
        this.name = name;
    }

    public NameMessage() {

    }

    public String getName() {
        return name;
    }
}
