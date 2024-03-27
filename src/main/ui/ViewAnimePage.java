package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewAnimePage implements ActionListener {

    JFrame frame;

    JMenuBar menuBar;
    JMenu file;
    JMenu shows;
    JMenu filter;

    JMenuItem saveFile;
    JMenuItem loadFile;
    JMenuItem addShow;
    JMenuItem deleteShow;
    JMenuItem planned;
    JMenuItem completed;
    JMenuItem watching;
    JMenuItem dropped;

    public ViewAnimePage() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("AniMosaic");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        menuBar = new JMenuBar();

        ImageIcon image = new ImageIcon("src/main/ui/images/cherry_blossom_icon.png"); //create an imageIcon
        frame.setIconImage(image.getImage()); //change icon of frame

        initMenuBar();

    }

    public void initMenuBar() {
        frame.setJMenuBar(menuBar);

        file = new JMenu("File");
        shows = new JMenu("Shows");
        filter = new JMenu("Filter");

        saveFile = new JMenuItem("Save File");
        loadFile = new JMenuItem("Load File");
        addShow = new JMenuItem("Add Show");
        deleteShow = new JMenuItem("Delete Show");
        completed = new JMenuItem("Completed");
        watching = new JMenuItem("Watching");
        planned = new JMenuItem("Planned");
        dropped = new JMenuItem("Dropped");

        addComponentsToMenuBar();
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
            System.out.println("Loading File..."); // HAVE THIS ACTUALLY LOAD A FILE
        } else if (e.getSource() == saveFile) {
            System.out.print("Saving File..."); // HAVE THIS ACTUALLY SAVE A FILE
        } else if (e.getSource() == addShow) {
            System.out.println("Adding show...");
        } else if (e.getSource() == deleteShow) {
            System.out.println("Deleting show...");
        } else if (e.getSource() == completed) {
            System.out.println("Filtering completed...");
        } else if (e.getSource() == watching) {
            System.out.println("Filtering watching...");
        } else if (e.getSource() == planned) {
            System.out.println("Filtering planned...");
        } else {
            System.out.println("Filtering dropped...");
        }
    }
}
