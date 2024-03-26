package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    I used Bro Code's "Java Login System" tutorial to guide the creation of my login screen
    https://www.youtube.com/watch?v=Hiv3gwJC5kw
 */

public class LoginPage implements ActionListener {

    private Map<String, String> loginInfo;

    private JFrame frame;
    private JButton loginButton;
    private JButton resetButton;
    private JTextField userIDField;
    private JPasswordField userPasswordField;
    private JLabel userId;
    private JLabel userPassword;
    private JLabel messageLabel;
    private JLabel sakura;
    private JLabel title;

    private static int WIDTH = 420;
    private static int HEIGHT = 420;

//    private Font myFont;
//    private static String fontPath = "src/main/ui/fonts/OleoScript-Bold.ttf";

    //MODIFIES: this
    //EFFECTS: creates new Login Page with given login info
    // Sets up GUI for login and Font for use
    public LoginPage(Map<String, String> originalLoginInfo) {
        loginInfo = new HashMap<String, String>(originalLoginInfo);
        frame = new JFrame();
        loginButton = new JButton("Login");
        resetButton = new JButton("Reset");
        userIDField = new JTextField();
        userPasswordField = new JPasswordField();
        userId = new JLabel("userID:");
        userPassword = new JLabel("Password: ");
        messageLabel = new JLabel(); // in case of error with input
        sakura = new JLabel();
        title = new JLabel();

        initGui();
        initSakuraPic();
    }

    public void initSakuraPic() {
        ImageIcon sakuraPic = new ImageIcon("src/main/ui/images/sakura.jpeg");
        sakura.setText("AniMosaic");
        sakura.setIcon(sakuraPic);
    }

    // EFFECTS: creates GUI for login page with all fields
    public void initGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("AniMosaic"); //sets title of frame
        ImageIcon image = new ImageIcon("src/main/ui/images/cherry_blossom_icon.png"); //create an imageIcon
        frame.setIconImage(image.getImage()); //change icon of frame
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

//        try {
//            myFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
//            myFont.deriveFont(20f);
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(myFont);
//
//        } catch (IOException | FontFormatException e) {
//            System.out.println("Font Not Recognized");
//        }

        initComponents();
        addToFrame();
    }


    public void initComponents() {
        userId.setBounds(50,100,75,25);
        userPassword.setBounds(50,150,75,25);
        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC,25));

        loginButton.setBounds(125,200, 100, 25);
        loginButton.setFocusable(false);
        loginButton.setBackground(Color.decode("#ffdad3"));
        loginButton.addActionListener(this);

        resetButton.setBounds(225, 200, 100, 25);
        resetButton.setFocusable(false);
        resetButton.setBackground(Color.decode("#ffc8dd"));
        resetButton.addActionListener(this);

        sakura.setBounds(50, 240, 299, 168);
        title.setText("AniMosaic");
        title.setFont(new Font("Serif", Font.ITALIC, 30));
        title.setBounds(160,20,200,100);

    }

    //EFFECTS: adds all components to frame
    public void addToFrame() {
        frame.add(userId);
        frame.add(userPassword);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(sakura);
        frame.add(title);
    }

    //MODIFIES: this
    //EFFECTS: clears userID and passwordFields
    @Override
    public void actionPerformed(ActionEvent e) {
        // "getSource()" gets object on which event is occuring i.e.
        // if the source object is the "reset button" --> someone CLICKING this button
        // then do xxxxxxxx
        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userPasswordField.setText("");
        }

        if (e.getSource() == loginButton) {
            String userId = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword()); //converts password to string

            if (loginInfo.containsKey(userId)) {
                if (loginInfo.get(userId).equals(password)) {
                    messageLabel.setForeground(Color.decode("#bbe784"));
                    messageLabel.setText("Login successful");
                    ViewAnimePage viewAnimePage = new ViewAnimePage();
                } else {
                    messageLabel.setForeground(Color.decode("#c61e09"));
                    messageLabel.setText("Wrong Password");
                }

            } else {
                messageLabel.setForeground(Color.decode("#c61e09"));
                messageLabel.setText("User Not Found");
            }
        }
    }
}