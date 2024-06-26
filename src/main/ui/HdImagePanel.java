package ui;

import javax.swing.*;
import java.awt.*;

/*
    CITATIONS:
    1) ImagePanel class help from Billy Li --> makes imported images much clearer
    https://edstem.org/us/courses/50358/discussion/4648716
 */

// JPanel using Graphics2D to render imported images in higher quality
public class HdImagePanel extends JPanel {

    private Image icon;
    private double scaleSize;

    // REQUIRES: icon of type Image or relevant subclasses
    // EFFECTS: Creates instance of loginpagebackgroundimagepanel with given icon
    public HdImagePanel(Image icon, Double scale) {
        super();
        this.icon = icon;
        this.scaleSize = scale;
    }

    // MODIFIES: this
    // EFFECTS: draws icon on specified frame/panel at specified scale and position
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // IMPORTANT
        // IMPORTANT: make sure you have this if you don't want blurry images
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.scale(scaleSize,scaleSize);
        g2.drawImage(icon, 0, 0, this);
    }

}