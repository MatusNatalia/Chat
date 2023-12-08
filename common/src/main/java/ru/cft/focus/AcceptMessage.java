package ru.cft.focus;

import java.util.List;

public class AcceptMessage extends Message {

    private boolean accepted;
    private String description;
    private List<String> onlineUsers;

    public AcceptMessage(boolean accepted, String description, List<String> onlineUsers) {
        super(MessageType.ACCEPT_MSG);
        this.accepted = accepted;
        this.description = description;
        this.onlineUsers = onlineUsers;
    }

    public AcceptMessage() {

    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }
}
