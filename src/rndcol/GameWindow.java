package rndcol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameWindow extends JFrame {
    private final Color[] targetColors = {
            Color.RED, Color.GREEN, Color.BLUE,
            Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.WHITE
    };
    private final String[] colorNames = {
            "Red", "Green", "Blue", "Yellow", "Cyan", "Magenta", "White"
    };

    private Color targetColor;
    private int score = 0;
    private final boolean[] rgb = new boolean[3];

    private final JLabel scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
    private final JLabel targetLabel = new JLabel("Target Color", SwingConstants.CENTER);
    private final JPanel targetColorPanel = new JPanel();
    private final JPanel userColorPanel = new JPanel();
    private final JButton[] colorButtons = new JButton[3];
    private final Color[] buttonColors = {Color.RED, Color.GREEN, Color.BLUE};

    private Timer hideTimer;
    private Timer validationTimer;
    private Timer countdownTimer;

    public GameWindow() {
        setTitle("RandCol Game");
        setIconImage(new ImageIcon("RGBhint.png").getImage());
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Choose background image based on dark mode setting
        String basePath = "/home/nira/Documents/Shay/RndColJava/RndColJava/";
        String imageFile = OptionsWindow.darkMode ? "Leiden002dm.jpg" : "Leiden001.jpg";

        Image bgImage = new ImageIcon(basePath + imageFile).getImage();
        BackgroundPanel bgPanel = new BackgroundPanel(bgImage);
        setContentPane(bgPanel);

// Optional fallback color (won’t be visible if image loads correctly)
        getContentPane().setBackground(Color.BLACK);


        setupUI(bgPanel);
        setupKeyBindings();
        generateNewTarget();

        setVisible(true);
    }

    private void setupUI(JPanel bgPanel) {
        int panelSize = 100;
        int centerX = getWidth() / 2 - panelSize / 2;

        JLabel playerLabel = new JLabel("Player: " + MenuWindow.playerName, SwingConstants.CENTER);
        playerLabel.setBounds(centerX - 100, 20, 200, 30);
        playerLabel.setForeground(Color.CYAN);

        scoreLabel.setBounds(centerX - 100, 50, 200, 30);
        scoreLabel.setForeground(Color.MAGENTA);

        targetLabel.setBounds(centerX - 100, 80, 200, 30);
        targetLabel.setForeground(Color.YELLOW);

        bgPanel.add(playerLabel);
        bgPanel.add(scoreLabel);
        bgPanel.add(targetLabel);

        targetColorPanel.setBounds(centerX, 130, panelSize, panelSize);
        targetColorPanel.setBackground(Color.BLACK);
        targetColorPanel.setOpaque(true);
        bgPanel.add(targetColorPanel);

        userColorPanel.setBounds(centerX, 370, panelSize, panelSize);
        userColorPanel.setBackground(Color.BLACK);
        userColorPanel.setOpaque(true);
        bgPanel.add(userColorPanel);

        String[] labels = {"Red", "Green", "Blue"};
        int buttonWidth = 80;
        int spacing = 20;
        int totalWidth = 3 * buttonWidth + 2 * spacing;
        int startX = getWidth() / 2 - totalWidth / 2;

        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton(labels[i]);
            btn.setBounds(startX + i * (buttonWidth + spacing), 500, buttonWidth, 30);
            btn.setEnabled(false);
            btn.setBackground(buttonColors[i]); // ✅ true color
            btn.setForeground(Color.WHITE);
            colorButtons[i] = btn;
            bgPanel.add(btn);
        }
    }

    private void setupKeyBindings() {
        JRootPane rootPane = getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("1"), "toggleRed");
        inputMap.put(KeyStroke.getKeyStroke("2"), "toggleGreen");
        inputMap.put(KeyStroke.getKeyStroke("3"), "toggleBlue");

        actionMap.put("toggleRed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { toggleColor(0); }
        });
        actionMap.put("toggleGreen", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { toggleColor(1); }
        });
        actionMap.put("toggleBlue", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { toggleColor(2); }
        });
    }

    private void toggleColor(int index) {
        rgb[index] = !rgb[index];
        colorButtons[index].setBackground(rgb[index] ? buttonColors[index] : Color.DARK_GRAY);
        updateUserColor();
    }

    private void updateUserColor() {
        Color userColor = new Color(rgb[0] ? 255 : 0, rgb[1] ? 255 : 0, rgb[2] ? 255 : 0);
        userColorPanel.setBackground(userColor);

        if (validationTimer != null && validationTimer.isRunning()) {
            validationTimer.stop();
        }

        validationTimer = new Timer(600, e -> validateUserColor(userColor));
        validationTimer.setRepeats(false);
        validationTimer.start();
    }

    private void validateUserColor(Color userColor) {
        if (userColor.equals(targetColor)) {
            score++;
            scoreLabel.setText("Score: " + score);
            if (score >= OptionsWindow.pointsToWin) {
                if (countdownTimer != null && countdownTimer.isRunning()) {
                    countdownTimer.stop();
                }
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "You nailed it, " + MenuWindow.playerName + "!\nWhat would you like to do next?",
                        "Victory!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Play Again", "Options", "Exit"},
                        "Play Again"
                );

                dispose(); // close current game window

                if (choice == 0) {
                    new GameWindow(); // Play Again
                } else if (choice == 1) {
                    new OptionsWindow(); // Open Options
                } else {
                    System.exit(0); // Exit to main menu
                }
            } else {
                if (countdownTimer != null && countdownTimer.isRunning()) {
                    countdownTimer.stop();
                }
                generateNewTarget();
            }
        } else if (rgb[0] || rgb[1] || rgb[2]) {
            score = 0;
            scoreLabel.setText("Score: 0");
            JOptionPane.showMessageDialog(this, "EEEEE WRONG COLOR!");
            generateNewTarget();
        }
    }

    private void generateNewTarget() {
        int index = new Random().nextInt(targetColors.length);
        targetColor = targetColors[index];
        targetLabel.setText("Target: " + colorNames[index]);
        targetColorPanel.setBackground(targetColor);
        resetUserColor();

        if (OptionsWindow.timeChallenge && OptionsWindow.timeLimitSeconds > 0) {
            if (countdownTimer != null && countdownTimer.isRunning()) {
                countdownTimer.stop();
            }

            countdownTimer = new Timer(OptionsWindow.timeLimitSeconds * 1000, e -> {
                score = 0;
                scoreLabel.setText("Score: 0");
                JOptionPane.showMessageDialog(this, "⏰ Time's up!");
                generateNewTarget();
            });
            countdownTimer.setRepeats(false);
            countdownTimer.start();
        }
    }

    private void resetUserColor() {
        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = false;
            colorButtons[i].setBackground(buttonColors[i]); // ✅ restore true color
        }
        userColorPanel.setBackground(Color.BLACK);
    }
}
