package ui;

import javax.swing.*;
import java.awt.*;


public class ViewAnimePage {

    JFrame frame;

    JPanel categories;
    JPanel animeLists;

    JButton completed;
    JButton planned;
    JButton watching;
    JButton dropped;

    JLabel completedLabel;
    JLabel plannedLabel;
    JLabel watchingLabel;
    JLabel droppedLabel;

    public ViewAnimePage() {
        frame = new JFrame(); //creates a frame
        frame.setTitle("AniMosaic"); //sets title of frame
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        frame.setResizable(false); //prevent screen size from being resized
        frame.setVisible(true); //make frame visible

        ImageIcon image = new ImageIcon("src/main/ui/images/cherry_blossom_icon.png"); //create an imageIcon
        frame.setIconImage(image.getImage()); //change icon of frame
    }

    public void setCategorySelection() {
        categories = new JPanel();
        frame.add(categories);

        categories.setLayout(new BoxLayout(categories, BoxLayout.LINE_AXIS));

        completed = new JButton("Completed");
        planned = new JButton("Planned");
        watching = new JButton("Watching");
        dropped = new JButton("Dropped");

        categories.add(completed);
        categories.add(planned);
        categories.add(dropped);
        categories.add(watching);

    }
}
