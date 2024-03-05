package ui;

import java.io.FileNotFoundException;

// Initializes AniMosaic()
public class Main {
    public static void main(String[] args) {
        try {
            new AniMosaic();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}