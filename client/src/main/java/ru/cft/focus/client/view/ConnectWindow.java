package ru.cft.focus.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnectWindow extends JDialog {

    private final Container contentPane;
    private JButton exitButton;
    private JTextField hostTextField;
    private JTextField portTextField;
    private ConnectButtonListener connectListener;

    public ConnectWindow(JFrame owner) {
        super(owner, "Server configuration", true);

        contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        createLabel();
        createFields();
        createButtons();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(300, 130));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void createLabel() {
        JLabel label = new JLabel("Enter host and port:");
        contentPane.add(label, contentPane);
    }

    private void createFields() {
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new GridLayout(2, 2));

        JLabel hostLabel = new JLabel("host:");
        hostTextField = new JTextField();

        JLabel portLabel = new JLabel("port:");
        portTextField = new JTextField();

        configPanel.add(hostLabel);
        configPanel.add(hostTextField);
        configPanel.add(portLabel);
        configPanel.add(portTextField);

        contentPane.add(configPanel, contentPane);
    }

    private void createButtons() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        JButton connectButton = new JButton("connect");
        connectButton.addActionListener(e -> {
            dispose();

            if (connectListener != null) {
                connectListener.onConnectButtonClick(hostTextField.getText(), portTextField.getText());
            }

        });

        exitButton = new JButton("exit");

        buttonsPanel.add(connectButton);
        buttonsPanel.add(exitButton);

        contentPane.add(buttonsPanel, contentPane);
    }

    public void setConnectButtonListener(ConnectButtonListener connectListener) {
        this.connectListener = connectListener;
    }

    public void setExitListener(ActionListener actionListener) {
        exitButton.addActionListener(actionListener);
    }

}
