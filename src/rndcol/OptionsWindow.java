package rndcol;

import javax.swing.*;
import java.awt.*;

public class OptionsWindow extends JFrame {
    public static boolean darkMode = false;
    public static boolean keepColorVisible = true;
    public static int pointsToWin = 5;
    public static boolean timeChallenge = false;
    public static int timeLimitSeconds = 0;


    public OptionsWindow() {
        setTitle("Game Options");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // ✅ Version label
        JLabel versionLabel = new JLabel("Version: 1.0");
        versionLabel.setBounds(10, 10, 100, 20);
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        add(versionLabel);

        // ✅ Points to Win label
        JLabel pointsLabel = new JLabel("Points to Win:");
        pointsLabel.setBounds(50, 50, 120, 25);
        add(pointsLabel);

        String[] pointOptions = {"5 Points to win", "10 Points to win", "15 Points to win"};
        JComboBox<String> pointsDropdown = new JComboBox<>(pointOptions);
        pointsDropdown.setBounds(180, 50, 150, 25);
        add(pointsDropdown);

        // ✅ Time Challenge label
        JLabel timeLabel = new JLabel("Time Limit:");
        timeLabel.setBounds(50, 90, 120, 25);
        add(timeLabel);

        String[] timeOptions = {"OFF", "30 seconds", "15 seconds", "10 seconds", "5 seconds", "2 seconds"};
        JComboBox<String> timeDropdown = new JComboBox<>(timeOptions);
        timeDropdown.setBounds(180, 90, 150, 25);
        add(timeDropdown);

        JCheckBox darkModeCheckbox = new JCheckBox("Dark Mode");
        darkModeCheckbox.setBounds(50, 130, 150, 25);
        add(darkModeCheckbox);

        JCheckBox keepColorCheckbox = new JCheckBox("Keep Target Color Visible");
        keepColorCheckbox.setBounds(50, 160, 250, 25);
        keepColorCheckbox.setSelected(true);
        add(keepColorCheckbox);

        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(150, 210, 100, 30);
        add(applyButton);

        applyButton.addActionListener(e -> {
            String selectedPoints = (String) pointsDropdown.getSelectedItem();
            pointsToWin = Integer.parseInt(selectedPoints.split(" ")[0]);
            String selectedTime = (String) timeDropdown.getSelectedItem();
            if (selectedTime.equals("OFF")) {
                timeChallenge = false;
                timeLimitSeconds = 0;
            } else {
                timeChallenge = true;
                timeLimitSeconds = Integer.parseInt(selectedTime.split(" ")[0]);
            }
            darkMode = darkModeCheckbox.isSelected();
            keepColorVisible = keepColorCheckbox.isSelected();
            dispose();
            new MenuWindow();
        });

        setVisible(true);
    }
}
