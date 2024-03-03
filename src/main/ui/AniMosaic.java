package ui;

import exceptions.NonSpecifiedCategoryException;
import exceptions.NonZeroNameLengthException;
import exceptions.ShowNonexistentException;
import model.Show;
import model.Library;

import java.util.ArrayList;
import java.util.Scanner;

// CITATION: CPSC 210 Teller App
// AniMosaic app
public class AniMosaic {
    private Library myLibrary;
    private Scanner input;
    String categories = ("\ncompleted \nwatching \nplanned \ndropped \n");
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<String> shows = new ArrayList<>();

    private final String numOutOfRangeException = "Given values out of range, please follow the specified range: ";
    private final String currentEpCountBigger = "Current ep count CANNOT be larger than total ep count. Please try "
            + "again: ";

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
        genres.add(genre);

        // Loop to ensure rank between 0-10
        ranking = rankingRangeSatisfiedCheck();

        System.out.print("Total episode number: ");
        totalEp = input.nextInt();
        while (totalEp <= 0) {
            System.out.print("Please provide a total episode value >0: ");
            totalEp = input.nextInt();
        }

        // Loop to ensure currentEp >= TotalEp
        currentEp = epRatioSatisfiedCheck(totalEp);

        Show newShow = new Show(name, genre, ranking, currentEp, totalEp);

        addToCategory(newShow);
        shows.add(name);
    }

    // MODIFIES: this
    // EFFECTS: adds or deletes comment from given show
    private void doComments() {
        System.out.println("Which show's comments would you like to edit?: ");
        System.out.println(shows);

        Show show = showExistsCheck();

        while (show == null) {
            show = showExistsCheck();
        }

        System.out.println("Would you like to 'add' or 'delete' a comment? ");
        String answer = input.next();

        while (!answer.equalsIgnoreCase("add") && !answer.equalsIgnoreCase("delete")) {
            System.out.println("That is not one of the options. Please specify 'add' or 'delete'");
            answer = input.next();
        }

        if (answer.equalsIgnoreCase("add")) {
            System.out.println("Please type out your comment: ");

            commentNotEmptyCheck(show);

            System.out.println("Comment successfully added. \n");
        } else if (answer.equalsIgnoreCase("delete")) {
            show.deleteComments();
            System.out.println("Comments have been deleted.\n");
        }

    }

    // MODIFIES: this
    // EFFECTS: deletes a show from the library
    private void doDeleteShow() {
        System.out.println("Which show would you like to delete?: ");
        System.out.print("Please select from the following: \n");
        System.out.println(shows);

        Show show = showExistsCheck();

        while (show == null) {
            show = showExistsCheck();
        }

        showExistsBeforeRemovalCheck(show);
    }

    // MODIFIES: this
    // EFFECTS: moves show from one category to another
    private void doTransferShow() {
        System.out.println("Which show would you like to transfer across your library?");
        System.out.println("Select from one of the following: \n");
        System.out.println(shows);

        Show show = showExistsCheck();

        while (show == null) {
            show = showExistsCheck();
        }

        String sourceName = showExistsInCategoryCheck(show);
        while (sourceName == null) {
            showExistsInCategoryCheck(show);
        }

        System.out.println(show.getName() + " is currently in the " + sourceName + " category.\n");
        System.out.println("Which category would you like to move it to? Please select from the following:");
        System.out.println(categories);

        String destination = input.next();

        if (!categories.contains(destination)) {
            System.out.println("That is not a valid category.\n");
        } else {
            showExistsBeforeRemovalCheck(show);
            categoryExistsForTransferCheck(show, destination);
            System.out.println(show.getName() + " has been added to " + destination);
        }
    }

    // EFFECTS: Displays given show in console if found, otherwise tells user show not in library
    private void doViewShow() {
        System.out.println("Please select from one of the following: \n");
        System.out.println(shows);

        Show show = showExistsCheck();

        while (show == null) {
            show = showExistsCheck();
        }

        System.out.println(show.toString());
    }

    // EFFECTS: Presents list of shows in specified category
    private void doFilterShow() {
        System.out.println("Which category would you like to view shows from?: ");
        System.out.println(categories);
        String category = input.next();

        while (!categories.contains(category)) {
            System.out.println("That is not a valid category. Please try again: ");
            category = input.next();
        }

        if (category.equalsIgnoreCase("completed")) {
            System.out.println(myLibrary.getCompleted());
        } else if (category.equalsIgnoreCase("watching")) {
            System.out.println(myLibrary.getWatching());
        } else if (category.equalsIgnoreCase("planned")) {
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
        System.out.print("Please select from the following: \n");
        System.out.println(shows);

        Show show = showExistsCheck();

        while (show == null) {
            show = showExistsCheck();
        }

        System.out.println("How many episodes would you like to add?: ");

        num = input.nextInt();

        while ((num + show.getCurrentEp()) > show.getTotalEp()) {
            System.out.println("This num will make your current episode number > total episodes.");
            System.out.println("Please provide a number that will not push current eps over the total value: ");
            num = input.nextInt();
        }

        show.setCurrentEp(num);
        System.out.println("Show episodes have been updated!\n");
        System.out.println(show.toString());
    }

    // EFFECTS: prompts user to continue giving category of show until it matches one of the 4 options
    private void addToCategory(Show newShow) {
        System.out.print("\nPlease type one of the following categories to add your show: ");
        System.out.print(categories);
        String category = input.next();
        // Add to specified category if types correctly
        boolean showFound = categoryExistsForTransferCheck(newShow, category);
        while (!showFound) {
            category = input.next();
            showFound = categoryExistsForTransferCheck(newShow, category);
        }

        System.out.print(newShow.getName() + " has been successfully added!\n");
    }

    // CHECK METHODS - CONTINUOUSLY PROMPTS USER UNTIL ACCEPTABLE RESPONSE PROVIDED

    // EFFECTS: get show in library from given input
    private Show showExistsCheck() {
        try {
            String name = input.next();
            Show show = myLibrary.findShow(name);
            return show;
        } catch (ShowNonexistentException sne) {
            System.out.println(sne.getMessage());
            return null;
        }
    }

    // EFFECTS: prompts user to continue giving current episode number until it satisfies condition
    private int epRatioSatisfiedCheck(int totalEp) {
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
    private int rankingRangeSatisfiedCheck() {
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

    // MODIFIES: comments of given show
    // EFFECTS: prompts user to continue giving comment until it satisfies condition
    private void commentNotEmptyCheck(Show show) {
        String newComment = "";
        while (newComment.length() == 0) {
            try {
                newComment = input.next();
                newComment = show.addComments(newComment);
            } catch (NonZeroNameLengthException nze) {
                System.out.println(nze.getMessage());
            }
        }
    }

    // EFFECTS: ensures show is in library when asked to remove, otherwise provides message
    private void showExistsBeforeRemovalCheck(Show show) {
        try {
            System.out.println(myLibrary.removeFromList(show));
        } catch (ShowNonexistentException sne) {
            System.out.println(sne.getMessage());
        }
    }

    // EFFECTS: returns show name if found in library, otherwise returns null
    private String showExistsInCategoryCheck(Show show) {
        try {
            String name = myLibrary.findCategoryName(show);
            return name;
        } catch (ShowNonexistentException sne) {
            System.out.println(sne.getMessage());
        }
        return null;
    }

    // EFFECTS: returns true if category exists for show to be added to, otherwise false
    private boolean categoryExistsForTransferCheck(Show show, String category) {
        try {
            myLibrary.addToList(show, category);
            return true;
        } catch (NonSpecifiedCategoryException nse) {
            System.out.println(nse.getMessage());
        }
        return false;
    }

}
