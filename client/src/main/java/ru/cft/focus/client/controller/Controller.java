package ru.cft.focus.client.controller;

import ru.cft.focus.client.Client;

public class Controller {

    private final Client client;

    public Controller(Client client) {
        this.client = client;
    }

    public void onConnect(String host, String port) {
        client.connectToServer(host, port);
    }

    public void onNameEntered(String name) {
        client.sendName(name);
    }

    public void onSendButtonClick(String message) {
        client.sendMessage(message);
    }

    public void onExit() {
        client.stop();
    }
}
