package ru.cft.focus;

public class UserStateChangedMessage extends Message {

    private boolean leftChat;
    private String name;

    public UserStateChangedMessage(Boolean leftChat, String name) {
        super(MessageType.USER_STATE_CHANGED_MSG);
        this.leftChat = leftChat;
        this.name = name;
    }

    public UserStateChangedMessage() {

    }

    public boolean leftChat() {
        return leftChat;
    }

    public String getName() {
        return name;
    }
}
