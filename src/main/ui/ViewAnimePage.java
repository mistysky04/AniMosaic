package ui;

import model.Library;
import model.Show;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/* To help me store screenHeight as int https://www.geeksforgeeks.org/convert-double-to-integer-in-java/
How I learned to calculated screenHeight for use in dimensions https://alvinalexander.com/blog/post/jfc-swing/
    how-determine-get-screen-size-java-swing-app/#:~:text=Once%20you%20have%20the%20screen,getWidth()%3B

*/

public class ViewAnimePage implements ActionListener {

    // CONSTANTS
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenHeight = (int) screenSize.getHeight();
    private static int screenWidth = (int) screenSize.getWidth();

    private static final Dimension showButtonDimensions = new Dimension(200,300);
    private static final Dimension sideBarPanelDimensions = new Dimension(200, screenHeight);
    private static final String JSON_STORE = "./data/library.json";

    // IMAGE PATHS
    private static final String sakuraPath = "src/main/ui/images/cherry_blossom_icon.png";
    private static final String saveIconPath = "src/main/ui/images/SM_PNG-01_resize.png";
    private static final String loadIconPath = "src/main/ui/images/SM_PNG-04_resize.png";
    private static final String addShowIconPath = "src/main/ui/images/SM_PNG-05_resize.png";
    private static final String deleteShowIconPath = "src/main/ui/images/SM_PNG-06_resize.png";


    // COLOUR PALETTE
    private static final String darkPurple = "#392f5a";
    private static final String darkPink = "#f092dd";
    private static final String medPink = "#ffaff0";
    private static final String lightPink = "#eec8e0";
    private static final String lightGreen = "#a8c7bb";

    // PANELS & FRAMES
    private JFrame frame;
    private JPanel allShows;
    private JPanel sideBar;

    // MENUS
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu filter;

    private JMenuItem saveFile;
    private JMenuItem loadFile;
    private JMenuItem planned;
    private JMenuItem completed;
    private JMenuItem watching;
    private JMenuItem dropped;

    // BUTTONS
    private ArrayList<JButton> showButtons;
    private JButton addShow;
    private JButton deleteShow;

    // LABELS
    private JLabel panelPic1;
    private JLabel panelPic2;

    // IMAGES
    private ImageIcon saveIcon;
    private ImageIcon loadIcon;
    private ImageIcon completedIcon;
    private ImageIcon watchingIcon;
    private ImageIcon plannedIcon;
    private ImageIcon droppedIcon;
    private ImageIcon sakura;


    // JSON
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // LIBRARY ITEMS
    private String[] categories = new String[4];
    private Library myLibrary;

    // EFFECTS: Initializes all components of ViewAnimePage GUI
    public ViewAnimePage() {
        myLibrary = new Library("My Library");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        showButtons = new ArrayList<>();
        allShows = new JPanel(new FlowLayout(FlowLayout.LEFT, 30,30));
        sideBar = new JPanel(null);
        frame = new JFrame();
        menuBar = new JMenuBar();
        sakura = new ImageIcon();

        categories[0] = "completed";
        categories[1] = "watching";
        categories[2] = "planned";
        categories[3] = "dropped";

        initFrame();

        allShows.setVisible(true);
        sideBar.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes all aspects of overall ViewAnimePage frame
    public void initFrame() {
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.WHITE);
        frame.setTitle("AniMosaic");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(allShows, BorderLayout.CENTER);
        frame.add(sideBar, BorderLayout.EAST);

        sakura = new ImageIcon(sakuraPath);
        frame.setIconImage(sakura.getImage());

        frame.setVisible(true);

        initMenuBar();
        addSideBar();
    }

    // MODIFIES: this
    // EFFECTS: initializes all components of JMenuBar
    public void initMenuBar() {
        frame.setJMenuBar(menuBar);

        file = new JMenu("File");
        filter = new JMenu("Filter");

        saveFile = new JMenuItem("Save Library");
        loadFile = new JMenuItem("Load Library");
        completed = new JMenuItem("Completed");
        watching = new JMenuItem("Watching");
        planned = new JMenuItem("Planned");
        dropped = new JMenuItem("Dropped");

        addComponentsToMenuBar();
        initPicsForMenuBar();
    }

    // MODIFIES: this
    // EFFECTS: Adds all JMenuItems to the JMenuBar, and adds ActionListener functionality to each
    public void addComponentsToMenuBar() {
        menuBar.add(file);
        menuBar.add(filter);
        file.add(saveFile);
        file.add(loadFile);
        filter.add(completed);
        filter.add(watching);
        filter.add(planned);
        filter.add(dropped);


        saveFile.addActionListener(this);
        loadFile.addActionListener(this);
        completed.addActionListener(this);
        watching.addActionListener(this);
        planned.addActionListener(this);
        dropped.addActionListener(this);

        frame.setVisible(true);
    }

    // EFFECTS: initializes ImageIcons for all JMenuItems
    public void initPicsForMenuBar() {
        saveIcon = new ImageIcon(saveIconPath);
        loadIcon = new ImageIcon(loadIconPath);
        completedIcon = new ImageIcon("src/main/ui/images/SM_PNG-07_resize.png");
        watchingIcon = new ImageIcon("src/main/ui/images/SM_PNG-08_resize.png");
        plannedIcon = new ImageIcon("src/main/ui/images/SM_PNG-09_resize.png");
        droppedIcon = new ImageIcon("src/main/ui/images/SM_PNG-10_resize.png");

        addPicsToMenuBar();
    }

    // MODIFIES: this
    // EFFECTS: sets ImageIcons sets all JMenuItems
    public void addPicsToMenuBar() {
        saveFile.setIcon(saveIcon);
        loadFile.setIcon(loadIcon);
        completed.setIcon(completedIcon);
        watching.setIcon(watchingIcon);
        planned.setIcon(plannedIcon);
        dropped.setIcon(droppedIcon);
    }

    // MODIFIES: this
    // EFFECTS: sets SideBar on side of frame
    public void addSideBar() {
        sideBar.setPreferredSize(sideBarPanelDimensions);
        sideBar.setBorder(BorderFactory.createEtchedBorder());

        initSideBarComponents();
    }

    // MODIFIES: this
    // EFFECTS: Initializes sidebar components
    public void initSideBarComponents() {
        addShow = new JButton("ADD");
        addShow.setFocusable(false);
        addShow.setBackground(Color.decode(darkPurple));
        addShow.setForeground(Color.decode(lightPink));
        addShow.setBounds(50, 100, 100, 75);
        addShow.setFont(new Font("Serif", Font.ITALIC, 16));
        addShow.addActionListener(this);

        deleteShow = new JButton("DELETE");
        deleteShow.setFocusable(false);
        deleteShow.setBackground(Color.decode(lightPink));
        deleteShow.setForeground(Color.decode(darkPurple));
        deleteShow.setBounds(50, 200, 100, 75);
        deleteShow.setFont(new Font("Serif", Font.ITALIC, 16));
        deleteShow.addActionListener(this);

        panelPic1 = new JLabel(new ImageIcon(addShowIconPath));
        panelPic1.setBounds(80,50, panelPic1.getIcon().getIconWidth(), panelPic1.getIcon().getIconHeight());

        panelPic2 = new JLabel(new ImageIcon(deleteShowIconPath));
        panelPic2.setBounds(80,300, panelPic2.getIcon().getIconWidth(), panelPic2.getIcon().getIconHeight());

        addSideBarComponents();
    }

    // MODIFIES: this
    // EFFECTS: adds sidebar components to the side bar
    public void addSideBarComponents() {
        addShow.setVisible(true);
        deleteShow.setVisible(true);
        panelPic1.setVisible(true);
        panelPic2.setVisible(true);

        sideBar.add(addShow);
        sideBar.add(deleteShow);
        sideBar.add(panelPic1);
        sideBar.add(panelPic2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadFile) {
            loadLibrary();
        } else if (e.getSource() == saveFile) {
            saveLibrary();
        } else if (e.getSource() == addShow) {
            addShow();
        } else if (e.getSource() == deleteShow) {
            System.out.println("Deleting show...");
        } else if (e.getSource() == completed) {
            System.out.println("Filtering completed...");
        } else if (e.getSource() == watching) {
            System.out.println("Filtering watching...");
        } else if (e.getSource() == planned) {
            System.out.println("Filtering planned...");
        } else if (e.getSource() == dropped) {
            System.out.println("Filtering dropped...");
        } else {
            for (JButton button : showButtons) {
                String show = e.getActionCommand();
                viewShowAttributes(show);
                return;
            }
        }
    }

    private void viewShowAttributes(String show) {
        Show givenShow = myLibrary.findShow(show);

        JOptionPane.showMessageDialog(null,
                givenShow.toString(),
                "Your Show", JOptionPane.INFORMATION_MESSAGE);

    }

    // MODIFIES: this
    // EFFECTS: adds show to given category
    private void addShow() {
        String name;
        String genre;
        int ranking;
        int currentEp;
        int totalEp;
        String category;

        name = JOptionPane.showInputDialog("Enter the show NAME:");
        genre = JOptionPane.showInputDialog("Enter the show GENRE:");

        // Loop to ensure rank between 0-10
        ranking = rankingCheck();
        totalEp = Integer.parseInt(JOptionPane.showInputDialog("Enter TOTAL EPISODE NUMBER:"));

        // Loop to ensure currentEp >= TotalEp
        currentEp = currentEpCheck(totalEp);
        String comments = "";
        Show newShow = new Show(name, genre, comments, ranking, currentEp, totalEp);

        category = JOptionPane.showInputDialog(null, "Choose a CATEGORY", "Categories",
                JOptionPane.QUESTION_MESSAGE,
                null,
                categories,"Regular").toString();
        myLibrary.addToList(newShow, category);

        displayShow(newShow);
    }

    public void displayShow(Show show) {
        JButton newShow = new JButton(show.getName());
        newShow.addActionListener(this);

        newShow.setPreferredSize(showButtonDimensions);
        newShow.setFocusable(false);
        newShow.setFont(new Font(Font.SERIF, Font.ITALIC, 18));

        if (myLibrary.findCategoryName(show) == "completed") {
            newShow.setBackground(Color.decode("#dbc3ff"));
        } else if (myLibrary.findCategoryName(show) == "watching") {
            newShow.setBackground(Color.decode("#ffe2ef"));
        } else if (myLibrary.findCategoryName(show) == "planned") {
            newShow.setBackground(Color.decode("#bbe784"));
        } else {
            newShow.setBackground(Color.decode("#93bdff"));
        }

        allShows.add(newShow);
        allShows.setVisible(false);
        allShows.setVisible(true);

        showButtons.add(newShow);
    }

    // EFFECTS: prompts user to continue giving ranking until it satisfies condition
    private int rankingCheck() {
        String rankingString;
        int ranking = 0;
        boolean success = false;


        while (!success) {
            try {
                rankingString = JOptionPane.showInputDialog("Enter Ranking from 0-10: ");
                ranking = Integer.parseInt(rankingString);
                if (ranking < 0 || ranking > 10) {
                    JOptionPane.showMessageDialog(null, "Value must be betwen 0-10",
                            "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    success = false;
                } else {
                    success = true;
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Input must be an INTEGER",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        }

        return ranking;
    }

    // EFFECTS: prompts user to continue giving current episode number until it satisfies condition
    private int currentEpCheck(int totalEp) {
        int currentEp;

        currentEp = Integer.parseInt(JOptionPane.showInputDialog("Enter CURRENT EPISODE number: "));
        while (currentEp > totalEp) {
            JOptionPane.showMessageDialog(null,
                    "Current episode cannot be greater than total episode number!",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            System.out.print("Please enter a number <= total episode number: ");
            currentEp = Integer.parseInt(JOptionPane.showInputDialog("Please enter a number <= total "
                    + "episode number: "));
        }
        return currentEp;
    }


    // EFFECTS: saves the library to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(myLibrary);
            jsonWriter.close();
            System.out.println("Saved " + myLibrary.getTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads library from file
    private void loadLibrary() {
        try {
            myLibrary = jsonReader.read();
            updateOnLoadLibrary();
            allShows.setVisible(false);
            allShows.setVisible(true);
            System.out.println("Loaded " + myLibrary.getTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void updateOnLoadLibrary() {
        ArrayList<Show> completed = myLibrary.getCompleted();
        ArrayList<Show> planned = myLibrary.getPlanned();
        ArrayList<Show> dropped = myLibrary.getDropped();
        ArrayList<Show> watching = myLibrary.getWatching();

        for (ArrayList<Show> list : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show show : list) {
                displayShow(show);
            }
        }

    }
}
