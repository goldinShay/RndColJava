package rndcol;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {
    public static String playerName = "Player"; // default fallback

    public MenuWindow() {
        setTitle("RandCol Main Menu");
        setIconImage(new ImageIcon("RGBhint.png").getImage());
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        JButton startButton = new JButton("Start Game");
        JButton optionsButton = new JButton("Options");
        JButton exitButton = new JButton("Exit");

        startButton.addActionListener(e -> {
            dispose();
            new GameWindow();
        });

        optionsButton.addActionListener(e -> {
            dispose();
            new OptionsWindow();
        });

        exitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(optionsButton);
        add(exitButton);

        setVisible(true);
    }
}
