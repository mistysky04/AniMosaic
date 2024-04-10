package ui;

import model.Show;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class EditEpNumberFrame extends JFrame implements ActionListener {

    private static final String sailorMoonBgPath = "./data/images/city_skyline.jpg";
    private static final String sakuraIconImagePath = "./data/images/cherry_blossom_icon.png";

    private Show show;

    private JButton addButton;
    private JButton removeButton;

    private JPanel textPanel;
    private JPanel numberPanel;

    private JLabel textLabel;
    private JLabel numberLabel;

    private int startingNum;

    private BufferedImage sailorMoonBg;
    private HdImagePanel backgroundPanel;
    private ImageIcon sakuraIcon;

    // COLOUR PALETTE
    private static final String darkPurple = "#392f5a";
    private static final String darkPink = "#f092dd";
    private static final String medPink = "#ffaff0";
    private static final String lightPink = "#eec8e0";
    private static final String lightGreen = "#a8c7bb";

    public EditEpNumberFrame(Show show) {
        this.show = show;
        startingNum = show.getCurrentEp();

        addButton = new JButton("+ ep");
        removeButton = new JButton("- ep");

        textLabel = new JLabel(show.getName());
        textPanel = new JPanel(new GridBagLayout());

        numberLabel = new JLabel(startingNum + " eps");
        numberPanel = new JPanel(new GridBagLayout());

        sakuraIcon = new ImageIcon(sakuraIconImagePath);
        try {
            sailorMoonBg = ImageIO.read(new File(sailorMoonBgPath));
        } catch (IOException e) {
            System.out.println("error loading image");
        }
        backgroundPanel = new HdImagePanel(sailorMoonBg, 0.7);

        setFrame();

    }

    public void setFrame() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setTitle("Edit Episode Number");
        this.setSize(420,420);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(sakuraIcon.getImage());
        this.setResizable(false);
        setLocationRelativeTo(null);
        this.setVisible(true);

        initPanelComponents();
        initButtonComponents();
        addToFrame();
    }

    public void initPanelComponents() {
        textLabel.setBounds(75,50, 150,50);
        textLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        textLabel.setForeground(Color.decode(darkPurple));

        textPanel.setBounds(50, 50, 300, 50);
        textPanel.setBackground(Color.decode(lightPink));
        textPanel.add(textLabel);

        numberLabel.setBounds(175,125,100,40);
        numberLabel.setFont((new Font(Font.SERIF,Font.BOLD, 25)));
        numberLabel.setForeground(Color.decode(darkPurple));

        numberPanel.setBounds(150, 125, 100, 40);
        numberPanel.setBackground(Color.decode(medPink));
        numberPanel.add(numberLabel);

        backgroundPanel.setBounds(0,0,420,420);
    }

    public void initButtonComponents() {
        addButton.setBounds(100,200, 100, 50);
        addButton.setFont((new Font(Font.SERIF,Font.BOLD, 25)));
        addButton.setFocusable(false);
        addButton.setBackground(Color.decode("#ffdad3"));
        addButton.addActionListener(this);

        removeButton.setBounds(200, 200, 100, 50);
        removeButton.setFont((new Font(Font.SERIF,Font.BOLD, 25)));
        removeButton.setFocusable(false);
        removeButton.setBackground(Color.decode("#ffc8dd"));
        removeButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds all components to frame
    public void addToFrame() {
        this.add(textPanel);
        this.add(numberPanel);
        this.add(addButton);
        this.add(removeButton);
        this.add(backgroundPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addValue();
        } else if (e.getSource() == removeButton) {
            removeValue();
        }
    }

    public void addValue() {
        startingNum++;
        numberLabel.setText(startingNum + " eps");

        if (startingNum > show.getTotalEp()) {
            JOptionPane.showMessageDialog(null, "Eps cannot go above max of " + show.getTotalEp(),
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            startingNum--;
            numberLabel.setText(startingNum + " eps");
        }

        show.setCurrentEp(startingNum);
    }

    public void removeValue() {
        startingNum--;
        numberLabel.setText(startingNum + " eps");

        if (startingNum < 0) {
            JOptionPane.showMessageDialog(null, "Eps cannot be less than 0",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            startingNum++;
            numberLabel.setText(startingNum + " eps");
        }

        show.setCurrentEp(startingNum);

    }
}
