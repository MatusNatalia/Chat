package ru.cft.focus.client.view;

import javax.swing.*;
import java.awt.*;

public class ErrorWindow extends JDialog {
    private final Container contentPane;
    private JLabel errorLabel;

    public ErrorWindow(JFrame owner) {
        super(owner, "Error", true);

        contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        createErrorLabel();
        createOkButton();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(200, 130));
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    private void createErrorLabel() {
        errorLabel = new JLabel();
        contentPane.add(errorLabel, contentPane);
    }

    private void createOkButton() {
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            dispose();
        });

        contentPane.add(okButton, contentPane);
    }

    public void setErrorMessage(String errorMessage) {
        errorLabel.setText("<html>" + errorMessage + "</html>");
    }
}
