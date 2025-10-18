package rndcol;

import javax.swing.*;
import java.awt.*;

public class OptionsWindow extends JFrame {
    public static boolean darkMode = false;
    public static boolean soundEnabled = true;
    public static boolean keepColorVisible = true;
    public static int timerSetting = 0; // 0 = off, 1 = easy, etc.
    public static int pointsToWin = 5;

    public OptionsWindow() {
        setTitle("Options");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        JCheckBox darkModeBox = new JCheckBox("Dark Mode", darkMode);
        JCheckBox soundBox = new JCheckBox("Enable Sound", soundEnabled);
        JCheckBox keepColorBox = new JCheckBox("Keep Target Color Visible", keepColorVisible);

        String[] timerOptions = {"Off", "Easy (15s)", "Medium (5s)", "Hard (2s)", "Crazy (1s)"};
        JComboBox<String> timerDropdown = new JComboBox<>(timerOptions);
        timerDropdown.setSelectedIndex(timerSetting);

        String[] pointOptions = {"5 Points to win", "10 Points to win", "15 Points to win", "20 Points to win "};
        JComboBox<String> pointsDropdown = new JComboBox<>(pointOptions);
        pointsDropdown.setSelectedItem(String.valueOf(pointsToWin));

        JButton backButton = new JButton("Back");

        add(darkModeBox);
        add(soundBox);
        add(keepColorBox);
        add(timerDropdown);
        add(pointsDropdown);
        add(backButton);

        backButton.addActionListener(e -> {
            darkMode = darkModeBox.isSelected();
            soundEnabled = soundBox.isSelected();
            keepColorVisible = keepColorBox.isSelected();
            timerSetting = timerDropdown.getSelectedIndex();
            pointsToWin = Integer.parseInt((String) pointsDropdown.getSelectedItem());
            dispose();
            new MenuWindow();
        });

        setVisible(true);
    }
}
