package ui;

import model.Show;
import model.Library;

import java.util.Scanner;


// AniMosaic app
public class AniMosaic extends Thread {
    private Library myLibrary;
    private Scanner input;
    String categories = ("\ncompleted \nwatching \nplanned \ndropped \n");

    // EFFECTS: runs the AniMosaic application
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
        } else if (command.equals("e")) {
            doComments();
        } else if (command.equals("v")) {
            doViewShow();
        } else if (command.equals("f")) {
            doFilterShow();
        } else if (command.equals("i")) {
            doIncreaseEp();
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
        System.out.println("\nWelcome to AniMosaic! The future of anime tracking apps~");
        System.out.println("\n What would you like to do today?: ");
        System.out.println("\ta -> ADD show");
        System.out.println("\te -> EDIT comments");
        System.out.println("\tf -> FILTER category");
        System.out.println("\tv -> VIEW show");
        System.out.println("\tt -> TRANSFER show");
        System.out.println("\td -> DELETE show");
        System.out.println("\ti -> INCREASE ep number");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds show to given category
    private void doAddShow() {
        String name;
        String genre;
        int ranking;
        int currentEp;
        int totalEp;

        System.out.println("\nPlease enter the requested information to add your show\n");

        System.out.print("Name: ");
        name = input.next();

        System.out.print("Genre: ");
        genre = input.next();

        ranking = rankingCheck();

        System.out.print("Total episode number: ");
        totalEp = input.nextInt();

        currentEp = currentEpCheck(totalEp);

        Show newShow = new Show(name, genre, ranking, currentEp, totalEp);

        addToCategory(newShow);
    }

    // MODIFIES: this
    // EFFECTS: adds or deletes comment from given show
    private void doComments() {
        System.out.println("Which show's comments would you like to edit?: ");
        String name = input.next();
        Show show = myLibrary.findShow(name);

        if (show == null) {
            System.out.println("\nThat show does not exist in your library!\n");
        }

        System.out.println("Would you like to 'add' or 'delete' a comment? ");
        String answer = input.next();

        if (answer.equals("add")) {
            System.out.println("Please type out your comment: ");
            String comment = input.next();
            show.addComments(comment);
            System.out.println("Comment successfully added.");
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
        System.out.println("Which show would you like to delete?: ");
        Show show = getShow();

        System.out.println(myLibrary.removeFromList(show));
    }

    // MODIFIES: this
    // EFFECTS: moves show from one category to another
    private void doTransferShow() {
        System.out.println("Which show would you like to transfer across your library?: ");

        Show show = getShow();
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

    // EFFECTS: Displays given show in console if found, otherwise tells user show not in library
    private void doViewShow() {
        System.out.println("Which show are you looking for?: ");
        Show show = getShow();

        if (show != null) {
            System.out.println(show.toString());
        } else {
            System.out.println("That show does not exist in your library!");
        }
    }

    // EFFECTS: Presents list of shows in specified category
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

    // MODIFIES: this
    // EFFECTS: Increases currentEp of specified show by num, unless resulting currentEp > totalEp
    private void doIncreaseEp() {
        int num;

        System.out.println("Which show would you like to update?: ");
        Show show = getShow();

        System.out.println("How many episodes would you like to add?: ");

        num = input.nextInt();

        while ((num + show.getCurrentEp()) > show.getTotalEp()) {
            System.out.println("This num will make your current episode number > total episodes.");
            System.out.println("Please provide a number that will not push current eps over the total value: ");
            num = input.nextInt();
        }

        show.setCurrentEp(num);
        System.out.println("Show episodes have been updated!");
        System.out.println(show.toString());
    }

    // EFFECTS: helper method to get show in library from given string
    private Show getShow() {
        String name = input.next();

        return myLibrary.findShow(name);
    }

    // EFFECTS: prompts user to continue giving current episode number until it satisfies condition
    private int currentEpCheck(int totalEp) {
        int currentEp;

        System.out.print("Current episode number: ");
        currentEp = input.nextInt();
        while (currentEp > totalEp) {
            System.out.println("Current episode cannot be greater than total episode number!");
            System.out.print("Please enter a number <= total episode number: ");
            currentEp = input.nextInt();
        }
        return currentEp;
    }

    // EFFECTS: prompts user to continue giving ranking until it satisfies condition
    private int rankingCheck() {
        int ranking;

        System.out.print("Ranking (0-10): ");
        ranking = input.nextInt();
        while (ranking < 0 || ranking > 10) {
            System.out.println("That value is not allowed!");
            System.out.print("Please enter a number between 0 - 10: ");
            ranking = input.nextInt();
        }
        return ranking;
    }

    // EFFECTS: prompts user to continue giving category of show until it matches one of the 4 options
    private void addToCategory(Show newShow) {
        System.out.print("\nPlease type one of the following categories to add your show: ");
        System.out.print(categories);
        String category = input.next();
        boolean hasCategory = categories.contains(category);
        // Add to specified category if types correctly
        if (hasCategory) {
            myLibrary.addToList(newShow, category);
        } else {
            System.out.println("\nCannot add to nonexistent category...\n");
        }
        System.out.println(newShow.getName() + " has been successfully added!\n");
    }
}
