package model;

import exceptions.NonSpecifiedCategoryException;
import exceptions.ShowNonexistentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

// Library has 4 categories for shows to be sorted into based on their watch status
// Users can find shows in any of the library fields OR add/remove shows from each field
public class Library {
    private ArrayList<Show> completed = new ArrayList<>();
    private ArrayList<Show> watching = new ArrayList<>();
    private ArrayList<Show> planned = new ArrayList<>();
    private ArrayList<Show> dropped = new ArrayList<>();

    private final String categoryException = "That category is not accepted. Please specify an accepted category: ";
    private final String noShowException = "That show is not in your library. Please enter a show from the specified"
            + "list: ";

    // EFFECTS: Create new instance of library with empty arrayLists
    public Library() {
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds Show to given category list
     */
    public void addToList(Show show, String category) throws NonSpecifiedCategoryException {

        if (category.equalsIgnoreCase("completed")) {
            completed.add(show);
        } else if (category.equalsIgnoreCase("watching")) {
            watching.add(show);
        } else if (category.equalsIgnoreCase("planned")) {
            planned.add(show);
        } else if (category.equalsIgnoreCase("dropped")) {
            dropped.add(show);
        } else {
            throw new NonSpecifiedCategoryException(categoryException);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes Show from its specific category, or prompts user that show is not in library
     */
    public String removeFromList(Show show) throws ShowNonexistentException {
        for (List<Show> list  : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show newShow : list) {
                if (newShow.getName().equalsIgnoreCase(show.getName())) {
                    list.remove(show);
                    return "done";
                }
            }
        }
        throw new ShowNonexistentException(noShowException);
    }

    /*
     * EFFECTS: Returns category of given show, or returns null if show not found in any list;
     */
    public String findCategoryName(Show show) throws ShowNonexistentException {
        if (completed.contains(show)) {
            return "completed";
        } else if (watching.contains(show)) {
            return "watching";
        } else if (planned.contains(show)) {
            return "planned";
        } else if (dropped.contains(show)) {
            return "dropped";
        } else {
            throw new ShowNonexistentException(noShowException);
        }
    }

    /*
     * EFFECTS: returns show if found in library, else returns null
     */
    public Show findShow(String showName) throws ShowNonexistentException {
        for (List<Show> list : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show show : list) {
                if (show.getName().equalsIgnoreCase(showName)) {
                    return show;
                }
            }
        }
        throw new ShowNonexistentException(noShowException);
    }

    // Setters & Getters
    public ArrayList<Show> getCompleted() {
        return completed;
    }

    public ArrayList<Show> getWatching() {
        return watching;
    }

    public ArrayList<Show> getPlanned() {
        return planned;
    }

    public ArrayList<Show> getDropped() {
        return dropped;
    }
}