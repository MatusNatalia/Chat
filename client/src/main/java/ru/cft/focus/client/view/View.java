package ru.cft.focus.client.view;

import ru.cft.focus.AcceptMessage;
import ru.cft.focus.Message;
import ru.cft.focus.TextMessage;
import ru.cft.focus.UserStateChangedMessage;
import ru.cft.focus.client.Client;
import ru.cft.focus.client.controller.Controller;
import ru.cft.focus.client.listeners.ConnectListener;
import ru.cft.focus.client.listeners.ErrorListener;
import ru.cft.focus.client.listeners.MessageListener;

import java.awt.*;

public class View implements MessageListener, ConnectListener, ErrorListener {

    private final MainWindow mainWindow;
    private final ConnectWindow connectWindow;
    private final NameWindow nameWindow;
    private final ErrorWindow errorWindow;

    public View(Controller controller, Client client) {
        client.addMessageListener(this);
        client.addConnectListener(this);
        client.addErrorListener(this);

        mainWindow = new MainWindow();
        mainWindow.setSize(new Dimension(500, 500));
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setSendButtonListener(controller::onSendButtonClick);

        nameWindow = new NameWindow(mainWindow);
        nameWindow.setNameListener(controller::onNameEntered);
        nameWindow.setExitListener(e -> {
            mainWindow.dispose();
            controller.onExit();
        });

        connectWindow = new ConnectWindow(mainWindow);
        connectWindow.setConnectButtonListener(controller::onConnect);
        connectWindow.setExitListener(e -> {
            mainWindow.dispose();
            controller.onExit();
        });

        errorWindow = new ErrorWindow(mainWindow);

        mainWindow.setVisible(true);
        connectWindow.setVisible(true);
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.getType()) {
            case ACCEPT_MSG -> {
                AcceptMessage acceptMessage = (AcceptMessage) message;
                if (((AcceptMessage) message).isAccepted()) {
                    nameWindow.dispose();
                    acceptMessage.getOnlineUsers().forEach(mainWindow::addOnlineUser);
                } else {
                    errorWindow.setErrorMessage(acceptMessage.getDescription());
                    errorWindow.setVisible(true);
                }
            }
            case TEXT_MSG -> {
                TextMessage textMessage = (TextMessage) message;
                mainWindow.addNewMessage(textMessage.getDate(), textMessage.getUserName(), textMessage.getText());
            }
            case USER_STATE_CHANGED_MSG -> {
                UserStateChangedMessage userStateChangedMessage = (UserStateChangedMessage) message;
                if (userStateChangedMessage.leftChat()) {
                    mainWindow.removeOnlineUser(userStateChangedMessage.getName());
                    mainWindow.addNewMessage(userStateChangedMessage.getDate(), userStateChangedMessage.getName(),
                            "left chat");
                } else {
                    mainWindow.addOnlineUser(userStateChangedMessage.getName());
                    mainWindow.addNewMessage(userStateChangedMessage.getDate(), userStateChangedMessage.getName(),
                            "joined chat");
                }
            }
        }
    }

    @Override
    public void handleError(String errorMessage) {
        mainWindow.clearChat();
        errorWindow.setErrorMessage(errorMessage);
        errorWindow.setVisible(true);
        connectWindow.setVisible(true);
    }

    @Override
    public void handleConnect() {
        nameWindow.setVisible(true);
    }
}
