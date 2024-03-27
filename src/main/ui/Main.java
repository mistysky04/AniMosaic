package ui;

import model.IdAndPasswords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.awt.GraphicsEnvironment;


// Initializes AniMosaic()
public class Main {
    public static void main(String[] args) {

        IdAndPasswords idAndPasswords = new IdAndPasswords(); // instance of ID and Passwords class in model
        new LoginPage(idAndPasswords.getLoginInfo());

        //new AniMosaicGui();

        //        try {
        //            new AniMosaic();
        //        } catch (FileNotFoundException e) {
        //            System.out.println("Unable to run application: file not found");
        //        }
    }

}