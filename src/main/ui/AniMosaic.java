package ui;

import model.Show;
import model.Library;

import java.util.Scanner;


// Bank teller application
public class AniMosaic {
    private Library myLibrary;
    private Scanner input;
    String categories = ("\ncompleted \nwatching \nplanned \ndropped \n");

    // EFFECTS: runs the teller application
    public AniMosaic() {
        runAniMosaic();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runAniMosaic() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddShow();
        } else if (command.equals("d")) {
            doDeleteShow();
        } else if (command.equals("t")) {
            doTransferShow();
        } else if (command.equals("c")) {
            doComments();
        } else if (command.equals("v")) {
            doViewShow();
        } else if (command.equals("f")) {
            doFilterShow();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes library, no shows currently added
    private void init() {
        myLibrary = new Library();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to AniMosaic! Select from the following options:");
        System.out.println("\ta -> add show");
        System.out.println("\tc -> edit comments");
        System.out.println("\tf -> filter category");
        System.out.println("\tv -> view show");
        System.out.println("\tt -> transfer show");
        System.out.println("\td -> delete show");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds show to category, asks if user wants to add comments
    private void doAddShow() {
        System.out.print("Enter the show name: ");
        // takes input from the keyboard
        String name = input.next();

        System.out.print("Enter the show genre: ");
        String genre = input.next();

        System.out.print("Enter your ranking: ");
        int ranking = input.nextInt();

        System.out.print("Enter the current episode you're on: ");
        int currentEp = input.nextInt();

        System.out.print("Enter the total number of episodes: ");
        int totalEp = input.nextInt();

        Show newShow = new Show(name, genre, ranking, currentEp, totalEp);

        System.out.print("Please type one of the following categories to add your show: ");
        System.out.print(categories);
        String category = input.next();
        boolean hasCategory = categories.contains(category);
        // Add to specified category if types correctly
        if (hasCategory) {
            myLibrary.addToList(newShow, category);
        } else {
            System.out.println("Cannot add to nonexistent category...\n");
        }
        System.out.println("Show has been successfully added!");
    }

    private void doComments() {
        System.out.println("Please type the show name you would like to edit comments from: ");
        String name = input.next();
        Show show = myLibrary.findShow(name);

        if (show == null) {
            System.out.println("That show does not exist in your library!");
        }

        System.out.println("Would you like to 'add' or 'delete' a comment? ");
        String answer = input.next();

        if (answer.equals("add")) {
            System.out.println("Please type out your comment: ");
            String comment = input.next();
            show.addComments(comment);
        } else if (answer.equals("delete")) {
            show.deleteComments();
            System.out.println("Comments have been deleted.");
        } else {
            System.out.println("That is not one of the options...\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a show from the library
    private void doDeleteShow() {
        System.out.println("Please type the show name you would like to remove from your library: ");
        String name = input.next();

        Show show = myLibrary.findShow(name);

        System.out.println(myLibrary.removeFromList(show));
    }

    // MODIFIES: this
    // EFFECTS: conducts a transfer
    private void doTransferShow() {
        System.out.println("Please type the show name you would like to transfer from your library: ");
        String name = input.next();

        Show show = myLibrary.findShow(name);
        String sourceName = myLibrary.findCategoryName(show);

        System.out.println(show.getName() + " is currently in the " + sourceName + " category.");
        System.out.println("Which category would you like to move it to? Please select from the following:");
        System.out.println(categories);

        String destination = input.next();

        if (!categories.contains(destination)) {
            System.out.println("That is not a valid category.");
        } else {
            myLibrary.removeFromList(show);
            myLibrary.addToList(show, destination);
            System.out.println(show.getName() + " has been added to " + destination);
        }
    }

    private void doViewShow() {
        System.out.println("Which show would you like to see?: ");
        String name = input.next();

        Show show = myLibrary.findShow(name);

        if (show != null) {
            System.out.println(show.toString());
        } else {
            System.out.println("That show does not exist in your library!");
        }
    }

    private void doFilterShow() {
        System.out.println("Which category would you like to view shows from?: ");
        System.out.println(categories);
        String category = input.next();

        if (!categories.contains(category)) {
            System.out.println("That is not a valid category.");
        } else if (category.equals("completed")) {
            System.out.println(myLibrary.getCompleted());
        } else if (category.equals("watching")) {
            System.out.println(myLibrary.getWatching());
        } else if (category.equals("planned")) {
            System.out.println(myLibrary.getPlanned());
        } else {
            System.out.println(myLibrary.getDropped());
        }
    }
}
