package ru.cft.focus.client.view;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class MainWindow extends JFrame {
    private final Container contentPane;

    private JLabel usersList;
    private JTextArea messages;
    private final Set<String> users = new HashSet<>();
    private final List<String> messagesList = new ArrayList<>();
    private SendButtonListener sendButtonListener;

    public MainWindow() {
        super("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        createMessageInputField();
        createUsersList();
        createMessagesField();
    }

    private void createMessageInputField() {
        JPanel inputFieldPanel = new JPanel();
        inputFieldPanel.setLayout(new BorderLayout());

        JTextField textField = new JTextField();
        JButton sendMessageButton = new JButton("send");
        sendMessageButton.addActionListener(e -> {
            if (sendButtonListener != null && !textField.getText().isBlank()) {
                sendButtonListener.onSendButtonClick(textField.getText());
            }
            textField.setText("");
        });

        inputFieldPanel.add(textField, BorderLayout.CENTER);
        inputFieldPanel.add(sendMessageButton, BorderLayout.EAST);

        contentPane.add(inputFieldPanel, BorderLayout.SOUTH);
    }

    private void createUsersList() {

        JPanel usersListPanel = new JPanel();
        usersListPanel.setLayout(new BorderLayout());

        JLabel listNameLabel = new JLabel("Online users:");

        usersList = new JLabel();
        usersList.setVerticalAlignment(SwingConstants.TOP);
        usersList.setHorizontalAlignment(SwingConstants.LEFT);

        usersListPanel.add(listNameLabel, BorderLayout.NORTH);
        usersListPanel.add(usersList, BorderLayout.CENTER);

        contentPane.add(usersListPanel, BorderLayout.EAST);
    }

    private void createMessagesField() {
        JPanel messagesPanel = new JPanel();
        messagesPanel.setLayout(new BorderLayout());

        messages = new JTextArea();
        messages.setLineWrap(true);
        messages.setWrapStyleWord(true);
        messages.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(messages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        messagesPanel.add(messages, BorderLayout.CENTER);
        messagesPanel.add(scrollPane, BorderLayout.EAST);

        contentPane.add(messagesPanel, BorderLayout.CENTER);
    }

    public void setSendButtonListener(SendButtonListener listener) {
        sendButtonListener = listener;
    }

    public void addOnlineUser(String name) {
        users.add(name);
        updateUsersList();
    }

    public void removeOnlineUser(String name) {
        users.remove(name);
        updateUsersList();
    }

    private void updateUsersList() {
        StringBuilder usersListText = new StringBuilder("<html>");
        users.forEach(user -> usersListText.append(user).append("<br>"));
        usersList.setText(usersListText.append("</html>").toString());
    }

    public void addNewMessage(Date date, String userName, String text) {
        messagesList.add("[" + new SimpleDateFormat("dd.MMM hh:mm:ss").format(date) + "] " +
                userName + ": " + text);
        updateMessagesList();
    }

    private void updateMessagesList() {
        StringBuilder messagesListText = new StringBuilder();
        messagesList.forEach(message -> messagesListText.append(message).append("\n"));
        messages.setText(messagesListText.toString());
    }

    public void clearChat() {
        messagesList.clear();
        users.clear();
        updateMessagesList();
        updateUsersList();
    }

}
