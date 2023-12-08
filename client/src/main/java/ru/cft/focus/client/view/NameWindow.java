package ru.cft.focus.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NameWindow extends JDialog {
    private final Container contentPane;
    private JButton exitButton;
    private JTextField nameTextField;
    private NameListener nameListener;

    public NameWindow(JFrame owner) {
        super(owner, "Name", true);

        contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        createLabel();
        createInputField();
        createButtons();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(300, 130));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void createLabel() {
        JLabel label = new JLabel("Enter your name:");
        contentPane.add(label, contentPane);
    }

    private void createInputField() {
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel("name:");
        nameTextField = new JTextField();

        configPanel.add(nameLabel);
        configPanel.add(nameTextField);

        contentPane.add(configPanel, contentPane);
    }

    private void createButtons() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        JButton connectButton = new JButton("join chat");
        connectButton.addActionListener(e -> {
            if (nameListener != null) {
                nameListener.onNameEntered(nameTextField.getText());
            }
        });

        exitButton = new JButton("exit");

        buttonsPanel.add(connectButton);
        buttonsPanel.add(exitButton);

        contentPane.add(buttonsPanel, contentPane);
    }

    public void setNameListener(NameListener nameListener) {
        this.nameListener = nameListener;
    }

    public void setExitListener(ActionListener actionListener) {
        exitButton.addActionListener(actionListener);
    }

}
