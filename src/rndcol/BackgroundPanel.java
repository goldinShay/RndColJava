package rndcol;

import javax.swing.*;
import java.awt.*;

class BackgroundPanel extends JPanel {
    private final Image background;

    public BackgroundPanel(Image background) {
        this.background = background;
        setLayout(null); // absolute positioning
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this); // ✅ scale to fill
    }
}
