package ui;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private Image icon;

    public ImagePanel(Image icon) {
        super();
        this.icon = icon;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // IMPORTANT
        // IMPORTANT: make sure you have this if you don't want blurry images
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // you can also scale it before drawing it here...
        g2.scale(0.40,0.40);
        g2.drawImage(icon, 0, 0, this);
    }

}