package ui;

import model.IdAndPasswords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;


// Initializes AniMosaic()
public class Main {
//    public static void main(String[] args) {
//        IdAndPasswords idAndPasswords = new IdAndPasswords(); // instance of ID and Passwords class in model
//        new LoginPage(idAndPasswords.getLoginInfo());
//
//        //new AniMosaicGui();
//
//        //        try {
//        //            new AniMosaic();
//        //        } catch (FileNotFoundException e) {
//        //            System.out.println("Unable to run application: file not found");
//        //        }
//    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(); //creates a frame
        frame.setTitle("AniMosaic"); //sets title of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        frame.setResizable(false); //prevent screen size from being resized
        frame.setSize(420, 420); // sets x and y of frame
        frame.setVisible(true); //make frame visible

        ImageIcon image = new ImageIcon("src/main/ui/images/cherry_blossom_icon.png"); //create an imageIcon
        frame.setIconImage(image.getImage()); //change icon of frame

        IdAndPasswords idAndPasswords = new IdAndPasswords();
        JLayeredPane layeredPane = new JLayeredPane();

        layeredPanel LoginPage(idAndPasswords.getLoginInfo());

        //new AniMosaicGui();

        //        try {
        //            new AniMosaic();
        //        } catch (FileNotFoundException e) {
        //            System.out.println("Unable to run application: file not found");
        //        }
    }
}