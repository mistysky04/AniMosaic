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


public class ViewAnimePage implements ActionListener {

    private JFrame frame;

    private JMenuBar menuBar;
    private JMenu file;
    private JMenu shows;
    private JMenu filter;

    private JMenuItem saveFile;
    private JMenuItem loadFile;
    private JMenuItem addShow;
    private JMenuItem deleteShow;
    private JMenuItem planned;
    private JMenuItem completed;
    private JMenuItem watching;
    private JMenuItem dropped;

    private ImageIcon saveIcon;
    private ImageIcon loadIcon;
    private ImageIcon addShowIcon;
    private ImageIcon deleteShowIcon;
    private ImageIcon completedIcon;
    private ImageIcon watchingIcon;
    private ImageIcon plannedIcon;
    private ImageIcon droppedIcon;

    private JPanel allShows;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/library.json";
    private String[] categories = new String[4];

    private static Dimension dimension1 = new Dimension(200,200);

    private Library myLibrary;

    private ArrayList<JButton> showButtons;

    public ViewAnimePage() {
        initFrame();

        myLibrary = new Library("My Library");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        categories[0] = "completed";
        categories[1] = "watching";
        categories[2] = "planned";
        categories[3] = "dropped";

        showButtons = new ArrayList<>();
    }

    public void initFrame() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.WHITE);
        frame.setTitle("AniMosaic");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        menuBar = new JMenuBar();

        ImageIcon image = new ImageIcon("src/main/ui/images/cherry_blossom_icon.png"); //create an imageIcon
        frame.setIconImage(image.getImage()); //change icon of frame


        allShows = new JPanel(new FlowLayout(FlowLayout.LEFT, 30,30));

        allShows.setVisible(true);
        frame.add(allShows, BorderLayout.CENTER);

        initMenuBar();
    }

    public void initMenuBar() {
        frame.setJMenuBar(menuBar);

        file = new JMenu("File");
        shows = new JMenu("Shows");
        filter = new JMenu("Filter");

        saveFile = new JMenuItem("Save Library");
        loadFile = new JMenuItem("Load Library");
        addShow = new JMenuItem("Add Show");
        deleteShow = new JMenuItem("Delete Show");
        completed = new JMenuItem("Completed");
        watching = new JMenuItem("Watching");
        planned = new JMenuItem("Planned");
        dropped = new JMenuItem("Dropped");

        addComponentsToMenuBar();
        initPicsForMenuBar();
    }

    public void initPicsForMenuBar() {

        saveIcon = new ImageIcon("src/main/ui/images/SM_PNG-01_resize.png");
        loadIcon = new ImageIcon("src/main/ui/images/SM_PNG-04_resize.png");
        addShowIcon = new ImageIcon("src/main/ui/images/SM_PNG-05_resize.png");
        deleteShowIcon = new ImageIcon("src/main/ui/images/SM_PNG-06_resize.png");
        completedIcon = new ImageIcon("src/main/ui/images/SM_PNG-07_resize.png");
        watchingIcon = new ImageIcon("src/main/ui/images/SM_PNG-08_resize.png");
        plannedIcon = new ImageIcon("src/main/ui/images/SM_PNG-09_resize.png");
        droppedIcon = new ImageIcon("src/main/ui/images/SM_PNG-10_resize.png");


        addPicsToMenuBar();
    }

    public void addPicsToMenuBar() {
        saveFile.setIcon(saveIcon);
        loadFile.setIcon(loadIcon);
        addShow.setIcon(addShowIcon);
        deleteShow.setIcon(deleteShowIcon);
        completed.setIcon(completedIcon);
        watching.setIcon(watchingIcon);
        planned.setIcon(plannedIcon);
        dropped.setIcon(droppedIcon);
    }

    public void addComponentsToMenuBar() {
        menuBar.add(file);
        menuBar.add(shows);
        menuBar.add(filter);
        file.add(saveFile);
        file.add(loadFile);
        shows.add(addShow);
        shows.add(deleteShow);
        filter.add(completed);
        filter.add(watching);
        filter.add(planned);
        filter.add(dropped);


        saveFile.addActionListener(this);
        loadFile.addActionListener(this);
        addShow.addActionListener(this);
        deleteShow.addActionListener(this);
        completed.addActionListener(this);
        watching.addActionListener(this);
        planned.addActionListener(this);
        dropped.addActionListener(this);

        frame.setVisible(true);
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

        newShow.setPreferredSize(dimension1);
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
            System.out.println("Loaded " + myLibrary.getTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
