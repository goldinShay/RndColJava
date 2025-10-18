package rndcol;

import javax.swing.*;
import java.awt.*;

public class NameEntryWindow extends JFrame {
    public NameEntryWindow() {
        setTitle("Enter Your Name");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel prompt = new JLabel("Enter your name:", SwingConstants.CENTER);
        JTextField nameField = new JTextField();
        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                MenuWindow.playerName = name;
                dispose();
                new MenuWindow(); // ✅ go to main menu
            } else {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            }
        });

        add(prompt, BorderLayout.NORTH);
        add(nameField, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
