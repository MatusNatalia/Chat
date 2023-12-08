package ru.cft.focus;


import ru.cft.focus.client.Client;
import ru.cft.focus.client.controller.Controller;
import ru.cft.focus.client.view.View;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Controller controller = new Controller(client);
        View view = new View(controller, client);
    }
}