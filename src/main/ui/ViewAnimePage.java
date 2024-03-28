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
import java.util.List;

/* To help me store screenHeight as int https://www.geeksforgeeks.org/convert-double-to-integer-in-java/
How I learned to calculated screenHeight for use in dimensions https://alvinalexander.com/blog/post/jfc-swing/
    how-determine-get-screen-size-java-swing-app/#:~:text=Once%20you%20have%20the%20screen,getWidth()%3B

    Checkbox icons <a href="https://www.flaticon.com/free-icons/okay" title="okay icons">Okay icons created by Bharat Icons - Flaticon</a>

    How to get custom buttons https://stackoverflow.com/questions/14591089/joptionpane-passing-custom-buttons

*/

public class ViewAnimePage implements ActionListener {

    // CONSTANTS
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenHeight = (int) screenSize.getHeight();

    private static final Dimension showButtonDimensions = new Dimension(175,275);
    private static final Dimension sideBarPanelDimensions = new Dimension(200, screenHeight);
    private static final String JSON_STORE = "./data/library.json";

    // IMAGE PATHS
    private static final String sakuraPath = "./data/images/cherry_blossom_icon.png";

    private static final String saveIconPath = "./data/images/SM_PNG-01_resize.png";
    private static final String loadIconPath = "./data/images/SM_PNG-04_resize.png";

    private static final String addShowIconPath = "./data/images/SM_PNG-05_resize.png";
    private static final String deleteShowIconPath = "./data/images/SM_PNG-06_resize.png";

    private static final String completedIconPath = "./data/images/SM_PNG-07_resize.png";
    private static final String watchingIconPath = "./data/images/SM_PNG-08_resize.png";
    private static final String plannedIconPath = "./data/images/SM_PNG-09_resize.png";
    private static final String droppedIconPath = "./data/images/SM_PNG-10_resize.png";
    private static final String resetIconPath = "./data/images/SM_PNG-11_resize.png";

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

    private JMenuItem saveFile;
    private JMenuItem loadFile;

    // BUTTONS
    private ArrayList<JButton> showButtons;
    private JButton addShow;
    private JButton deleteShow;

    // LABELS
    private JLabel panelPic1;
    private JLabel panelPic2;

    // RADIO BUTTONS
    private ButtonGroup filters;

    private JRadioButton completed;
    private JRadioButton watching;
    private JRadioButton planned;
    private JRadioButton dropped;
    private JRadioButton reset;

    // IMAGES
    private ImageIcon saveIcon;
    private ImageIcon loadIcon;
    private ImageIcon completedIcon;
    private ImageIcon watchingIcon;
    private ImageIcon plannedIcon;
    private ImageIcon droppedIcon;
    private ImageIcon resetIcon;
    private ImageIcon sakura;

    // JSON
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // LIBRARY ITEMS
    private String[] categories = new String[4];
    private Library myLibrary;
    private ArrayList<Show> completedList;
    private ArrayList<Show> watchingList;
    private ArrayList<Show> plannedList;
    private ArrayList<Show> droppedList;

    // SCROLL PANE
    private JScrollPane scrollShows;
    private WrapLayout wrapLayout;


    // EFFECTS: Initializes all components of ViewAnimePage GUI
    public ViewAnimePage() {
        myLibrary = new Library("My Library");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        showButtons = new ArrayList<>();
        wrapLayout = new WrapLayout(FlowLayout.LEFT, 30,30);

        allShows = new JPanel(wrapLayout);
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

        frame.add(sideBar, BorderLayout.EAST);

        sakura = new ImageIcon(sakuraPath);
        frame.setIconImage(sakura.getImage());

        frame.setVisible(true);

        initMenuBar();
        addSideBar();

        scrollShows = new JScrollPane(allShows);
        scrollShows.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollShows.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollShows, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes all components of JMenuBar
    public void initMenuBar() {
        frame.setJMenuBar(menuBar);

        file = new JMenu("File");

        saveFile = new JMenuItem("Save Library");
        loadFile = new JMenuItem("Load Library");

        addComponentsToMenuBar();
        initPicsForMenuBar();
    }

    // MODIFIES: this
    // EFFECTS: Adds all JMenuItems to the JMenuBar, and adds ActionListener functionality to each
    public void addComponentsToMenuBar() {
        menuBar.add(file);

        file.add(saveFile);
        file.add(loadFile);

        saveFile.addActionListener(this);
        loadFile.addActionListener(this);

        frame.setVisible(true);
    }

    // EFFECTS: initializes ImageIcons for all JMenuItems
    public void initPicsForMenuBar() {
        saveIcon = new ImageIcon(saveIconPath);
        loadIcon = new ImageIcon(loadIconPath);

        addPicsToMenuBar();
    }

    // MODIFIES: this
    // EFFECTS: sets ImageIcons sets all JMenuItems
    public void addPicsToMenuBar() {
        saveFile.setIcon(saveIcon);
        loadFile.setIcon(loadIcon);
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
        deleteShow = new JButton("DELETE");

        panelPic1 = new JLabel(new ImageIcon(addShowIconPath));
        panelPic2 = new JLabel(new ImageIcon(deleteShowIconPath));

        filters = new ButtonGroup();
        completed = new JRadioButton("Completed");
        watching = new JRadioButton("Watching");
        planned = new JRadioButton("Planned");
        dropped = new JRadioButton("Dropped");
        reset = new JRadioButton("Reset");

        completedIcon = new ImageIcon(completedIconPath);
        watchingIcon = new ImageIcon(watchingIconPath);
        plannedIcon = new ImageIcon(plannedIconPath);
        droppedIcon = new ImageIcon(droppedIconPath);
        resetIcon = new ImageIcon(resetIconPath);

        setSideBarComponents();
        addSideBarComponents();
        addButtonsToGroup();
        initRadioButtonsForFilter();
    }

    // MODIFIES: this
    // EFFECTS: sets all necessary aspects of sidebar components
    public void setSideBarComponents() {
        addShow.setFocusable(false);
        addShow.setBackground(Color.decode(darkPurple));
        addShow.setForeground(Color.decode(lightPink));
        addShow.setBounds(50, 100, 100, 75);
        addShow.setFont(new Font("Serif", Font.ITALIC, 16));
        addShow.addActionListener(this);

        deleteShow.setFocusable(false);
        deleteShow.setBackground(Color.decode(lightPink));
        deleteShow.setForeground(Color.decode(darkPurple));
        deleteShow.setBounds(50, 200, 100, 75);
        deleteShow.setFont(new Font("Serif", Font.ITALIC, 16));
        deleteShow.addActionListener(this);

        panelPic1.setBounds(80,50, panelPic1.getIcon().getIconWidth(), panelPic1.getIcon().getIconHeight());
        panelPic2.setBounds(80,300, panelPic2.getIcon().getIconWidth(), panelPic2.getIcon().getIconHeight());
    }

    public void addButtonsToGroup() {
        filters.add(completed);
        filters.add(watching);
        filters.add(planned);
        filters.add(dropped);
        filters.add(reset);
    }

    // MODIFIES: this
    // EFFECTS: updates radio buttons
    public void initRadioButtonsForFilter() {
        completed.setBounds(25, 400, 150, 50);
        completed.setFont(new Font("Serif", Font.ITALIC, 14));
        completed.setIcon(completedIcon);
        completed.addActionListener(this);

        watching.setBounds(25, 475, 150, 50);
        watching.setFont(new Font("Serif", Font.ITALIC, 14));
        watching.setIcon(watchingIcon);
        watching.addActionListener(this);

        planned.setBounds(25, 550, 150, 50);
        planned.setFont(new Font("Serif", Font.ITALIC, 14));
        planned.setIcon(plannedIcon);
        planned.addActionListener(this);

        dropped.setBounds(25, 625, 150, 50);
        dropped.setFont(new Font("Serif", Font.ITALIC, 14));
        dropped.setIcon(droppedIcon);
        dropped.addActionListener(this);

        reset.setBounds(25, 700, 150, 50);
        reset.setFont(new Font("Serif", Font.ITALIC, 14));
        reset.setIcon(resetIcon);
        reset.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds sidebar components to the side bar
    public void addSideBarComponents() {
        addShow.setVisible(true);
        deleteShow.setVisible(true);
        panelPic1.setVisible(true);
        panelPic2.setVisible(true);
        completed.setVisible(true);
        watching.setVisible(true);
        planned.setVisible(true);
        deleteShow.setVisible(true);

        sideBar.add(addShow);
        sideBar.add(deleteShow);
        sideBar.add(panelPic1);
        sideBar.add(panelPic2);

        sideBar.add(completed);
        sideBar.add(watching);
        sideBar.add(planned);
        sideBar.add(dropped);
        sideBar.add(reset);
    }

    @Override
    @SuppressWarnings("checkstyle:MethodLength")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadFile) {
            loadLibrary();
        } else if (e.getSource() == saveFile) {
            saveLibrary();
        } else if (e.getSource() == addShow) {
            addShow();
        } else if (e.getSource() == deleteShow) {
            deleteShow();
        } else if (e.getSource() == completed) {
            getLists(watchingList, plannedList, droppedList);
        } else if (e.getSource() == watching) {
            getLists(completedList, plannedList, droppedList);
        } else if (e.getSource() == planned) {
            getLists(completedList, watchingList, droppedList);
        } else if (e.getSource() == dropped) {
            getLists(completedList, plannedList, watchingList);
        } else if (e.getSource() == reset) {
            updateOnLoadLibrary();
        } else {
            showInfo(e);
        }
    }

    public void getLists(ArrayList<Show> list1, ArrayList<Show> list2, ArrayList<Show> list3) {
        completedList = myLibrary.getCompleted();
        watchingList = myLibrary.getWatching();
        plannedList = myLibrary.getPlanned();
        droppedList = myLibrary.getDropped();

        filterShows(list1, list2, list3);
    }

    public void filterShows(ArrayList<Show> category1, ArrayList<Show> category2, ArrayList<Show> category3) {

        for (ArrayList<Show> list : Arrays.asList(category1, category2, category3)) {
            for (Show show : list) {
                showButtons.remove(show);
                for (JButton button : showButtons) {
                    if (button.getText() == show.getName()) {
                        allShows.remove(button);
                    }
                }
            }
        }

        allShows.setVisible(false);
        allShows.setVisible(true);
    }

    public void showInfo(ActionEvent e) {
        for (JButton button : showButtons) {
            String show = e.getActionCommand();
            viewShowAttributes(show);
            return;
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

    // MODIFIES: this
    // EFFECTS: deletes a show from the library
    public void deleteShow() {
        String showName = JOptionPane.showInputDialog("Please select from the following shows to remove from your "
                + "library: " + getTotalShowList());

        Show show = myLibrary.findShow(showName);

        while (show == null) {
            JOptionPane.showMessageDialog(null,"Show does not exist in library",
                    "Show Nonexistent", JOptionPane.INFORMATION_MESSAGE);
            showName = JOptionPane.showInputDialog("Please select from the following shows to remove from your "
                    + "library: " + getTotalShowList());
            show = myLibrary.findShow(showName);
        }

        showButtons.remove(show);

        for (JButton button : showButtons) {
            if (button.getText() == show.getName()) {
                allShows.remove(button);
            }
        }

        allShows.setVisible(false);
        allShows.setVisible(true);

        JOptionPane.showMessageDialog(null, myLibrary.removeFromList(show), "Removed",
                JOptionPane.INFORMATION_MESSAGE);

    }

    // EFFECTS: Returns list of show names from all 4 categories
    private ArrayList<String> getTotalShowList() {
        ArrayList<String> shows = new ArrayList<>();
        ArrayList<Show> completed = myLibrary.getCompleted();
        ArrayList<Show> planned = myLibrary.getPlanned();
        ArrayList<Show> watching = myLibrary.getWatching();
        ArrayList<Show> dropped = myLibrary.getDropped();


        for (List<Show> list : Arrays.asList(completed, planned, watching, dropped)) {
            for (Show show : list) {
                shows.add(show.getName());
            }
        }
        return shows;
    }


    // EFFECTS: saves the library to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(myLibrary);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved " + myLibrary.getTitle() + " to "
                    + JSON_STORE, "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to save library",
                    "Save Unsuccessful", JOptionPane.WARNING_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Loaded " + myLibrary.getTitle() + " from "
                    + JSON_STORE, "Load Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load library",
                    "Load Unsuccessful", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateOnLoadLibrary() {
        allShows.removeAll();

        completedList = myLibrary.getCompleted();
        plannedList = myLibrary.getPlanned();
        droppedList = myLibrary.getDropped();
        watchingList = myLibrary.getWatching();

        for (ArrayList<Show> list : Arrays.asList(completedList, watchingList, plannedList, droppedList)) {
            for (Show show : list) {
                displayShow(show);
            }
        }

        allShows.setVisible(false);
        allShows.setVisible(true);

    }
}
