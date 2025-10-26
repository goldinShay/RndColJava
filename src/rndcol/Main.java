package rndcol;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummyFrame = new JFrame(); // temporary frame to set icon globally
            dummyFrame.setIconImage(new ImageIcon("RGBhint.png").getImage());
            dummyFrame.dispose(); // no need to show it

            NameEntryWindow window = new NameEntryWindow();
            window.setIconImage(new ImageIcon("RGBhint.png").getImage());
        });
    }
}
