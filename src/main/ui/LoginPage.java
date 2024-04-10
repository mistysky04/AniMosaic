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
    CITATIONS:
    1) Bro Code "Java Login System" tutorial --> guided creation of login page
    https://www.youtube.com/watch?v=Hiv3gwJC5kw

    2) Resizing image information https://cloudinary.com/guides/bulk-image-resize/3-ways-to-resize-images-in-java#:~
    :text=You%20can%20resize%20an%20image,as%20an%20array%20of%20pixels.

    3) Using BufferedImage to load image and utilize Graphics 2D
    https://stackoverflow.com/questions/53291767/draw-image-2d-graphics

    4) ImagePanel class help from Billy Li --> makes imported images much clearer
     https://edstem.org/us/courses/50358/discussion/4648716
 */

// LoginPage creates the GUI for a login page, prompting the user for their userID and Password
public class LoginPage implements ActionListener {

    // CONSTANTS
    // IMAGE PATHS
    private static final String sakuraBackgroundImagePath = "./data/images/full_cherry_blossom_background.png";
    private static final String sakuraIconImagePath = "./data/images/cherry_blossom_icon.png";

    // LOGIN
    private Map<String, String> loginInfo;

    // FRAMES
    private JFrame frame;

    // BUTTONS
    private JButton loginButton;
    private JButton resetButton;

    // TEXT/PASSWORD FIELDS
    private JTextField userIDField;
    private JPasswordField userPasswordField;

    // JLABELS
    private JLabel userId;
    private JLabel userPassword;
    private JLabel messageLabel;
    private JLabel title;


    // IMAGES
    private BufferedImage sakuraBackgroundImage;
    private HdImagePanel backgroundPanel;
    private ImageIcon sakuraIcon;

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
        title = new JLabel();
        messageLabel = new JLabel();

        sakuraIcon = new ImageIcon(sakuraIconImagePath);
        try {
            sakuraBackgroundImage = ImageIO.read(new File(sakuraBackgroundImagePath));
        } catch (IOException e) {
            System.out.println("error loading image");
        }
        backgroundPanel = new HdImagePanel(sakuraBackgroundImage, 0.40);

        initGui();
    }

    // MODIFIES: this
    // EFFECTS: creates GUI for login page with specified components and attributes
    public void initGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("AniMosaic"); //sets title of frame
        frame.setIconImage(sakuraIcon.getImage()); //change icon of frame
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        initComponents();
        addToFrame();
    }


    // EFFECTS: sets aspects of login page components, also adds ActionListeners to all buttons
    public void initComponents() {
        userId.setBounds(50,100,75,25);
        userPassword.setBounds(50,150,75,25);
        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(Font.SERIF, Font.ITALIC, 25));


        loginButton.setBounds(125,200, 100, 25);
        loginButton.setFocusable(false);
        loginButton.setBackground(Color.decode("#ffdad3"));
        loginButton.addActionListener(this);

        resetButton.setBounds(225, 200, 100, 25);
        resetButton.setFocusable(false);
        resetButton.setBackground(Color.decode("#ffc8dd"));
        resetButton.addActionListener(this);

        backgroundPanel.setBounds(0,0,420,420);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to frame
    public void addToFrame() {
        frame.add(userId);
        frame.add(userPassword);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(backgroundPanel);
        frame.add(title);
    }

    //MODIFIES: this
    //EFFECTS: clears page if resetButton pressed, checks inputted information if loginButton pressed
    // logs user in if information correct, otherwise prompts user to retry
    @Override
    public void actionPerformed(ActionEvent e) {
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

                    new AniMosaicGUI();

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