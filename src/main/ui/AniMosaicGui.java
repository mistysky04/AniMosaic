package ui;

import javax.swing.*;
import java.awt.*;

public class AniMosaicGui extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;



    public AniMosaicGui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("AniMosaic");
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);

        ImageIcon image = new ImageIcon("src/main/ui/images/cherry_blossom_icon.png"); //create an imageIcon
        this.setIconImage(image.getImage()); //change icon of frame

        this.getContentPane().setBackground(Color.white); //change color of background

    }
}
