package ui;

import model.Show;
import model.Library;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

// CITATION: CPSC 210 Teller App
// CITATION: CPSC 210 Serialization Demo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// AniMosaic app
public class AniMosaic {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/library.json";

    private Library myLibrary;
    private Scanner input;
    String categories = ("\ncompleted \nwatching \nplanned \ndropped \n");

    // EFFECTS: runs the AniMosaic application
    public AniMosaic() throws FileNotFoundException {

        input = new Scanner(System.in);
        myLibrary = new Library("My Library");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runAniMosaic();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runAniMosaic() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);
        input.useDelimiter("\n");

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
        } else if (command.equals("s")) {
            saveLibrary();
        } else if (command.equals("l")) {
            loadLibrary();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to AniMosaic! The future of anime tracking apps~");
        System.out.println("\n What would you like to do today?: ");
        System.out.println("\ts -> SAVE library to file");
        System.out.println("\tl -> LOAD library to file");
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

        // Loop to ensure rank between 0-10
        ranking = rankingCheck();

        System.out.print("Total episode number: ");
        totalEp = input.nextInt();

        // Loop to ensure currentEp >= TotalEp
        currentEp = currentEpCheck(totalEp);

        String comments = "";

        Show newShow = new Show(name, genre, comments, ranking, currentEp, totalEp);

        if (!addToCategory(newShow)) {
            return;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds or deletes comment from given show
    private void doComments() {
        System.out.println("Which show's comments would you like to edit?: ");
        System.out.println("Please select from the following: \n");
        System.out.println(getTotalShowList());

        Show show = getShow();

        if (!checkShow(show)) {
            return;
        }

        System.out.println("Would you like to 'add' or 'delete' a comment? ");
        String answer = input.next();

        if (answer.equalsIgnoreCase("add")) {
            System.out.println("Please type out your comment: ");
            String comment = input.next();
            show.addComments(comment);
            System.out.println("Comment successfully added. \n");
        } else if (answer.equalsIgnoreCase("delete")) {
            show.deleteComments();
            System.out.println("Comments have been deleted.\n");
        } else {
            System.out.println("That is not one of the options...\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a show from the library
    private void doDeleteShow() {
        System.out.println("Which show would you like to delete?: ");
        System.out.print("Please select from the following: \n");
        System.out.println(getTotalShowList());

        Show show = getShow();
        if (!checkShow(show)) {
            return;
        }

        System.out.println(myLibrary.removeFromList(show));
    }

    // MODIFIES: this
    // EFFECTS: moves show from one category to another
    private void doTransferShow() {
        System.out.println("Which show would you like to transfer across your library?");
        System.out.println("Select from one of the following: \n");
        System.out.println(getTotalShowList());

        Show show = getShow();
        if (!checkShow(show)) {
            return;
        }

        String sourceName = myLibrary.findCategoryName(show);

        System.out.println(show.getName() + " is currently in the " + sourceName + " category.\n");
        System.out.println("Which category would you like to move it to? Please select from the following:");
        System.out.println(categories);

        String destination = input.next();

        if (!categories.contains(destination)) {
            System.out.println("That is not a valid category.\n");
        } else {
            myLibrary.removeFromList(show);
            myLibrary.addToList(show, destination);
            System.out.println(show.getName() + " has been added to " + destination);
        }
    }

    // EFFECTS: Displays given show in console if found, otherwise tells user show not in library
    private void doViewShow() {
        System.out.println("Please select from one of the following: \n");
        System.out.println(getTotalShowList());

        Show show = getShow();
        if (!checkShow(show)) {
            return;
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
        System.out.print("Please select from the following: \n");
        System.out.println(getTotalShowList());

        Show show = getShow();
        if (checkShow(show) == false) {
            return;
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


    // EFFECTS: get show in library from given input
    private Show getShow() {
        String name = input.next();
        return myLibrary.findShow(name);
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

    // EFFECTS: returns true if show found in library, else false
    private boolean checkShow(Show show) {
        if (show != null) {
            System.out.println(show.toString());
            return true;
        } else {
            System.out.println("That show does not exist in your library!\n");
            return false;
        }
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
    private boolean addToCategory(Show newShow) {
        System.out.print("\nPlease type one of the following categories to add your show: ");
        System.out.print(categories);
        String category = input.next();
        boolean hasCategory = categories.contains(category);
        // Add to specified category if types correctly
        if (hasCategory) {
            myLibrary.addToList(newShow, category);
            System.out.println(newShow.getName() + " has been successfully added!\n");
            return true;
        } else {
            System.out.println("\nCannot add to nonexistent category...\n");
            return false;
        }
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