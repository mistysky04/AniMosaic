package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Library has 4 categories for shows to be sorted into based on their watch status
// Users can find shows in any of the library fields OR add/remove shows from each field
public class Library {
    private ArrayList<Show> completed = new ArrayList<>();
    private ArrayList<Show> watching = new ArrayList<>();
    private ArrayList<Show> planned = new ArrayList<>();
    private ArrayList<Show> dropped = new ArrayList<>();

    private ArrayList<String> allShows = new ArrayList<>();

    // EFFECTS: Create new instance of library with empty arrayLists
    public Library() {
    }

    /*
     * REQUIRES: category must be one of Library fields
     * MODIFIES: this
     * EFFECTS: adds Show to given category list
     */
    public void addToList(Show show, String category) {
        if (category.equalsIgnoreCase("completed")) {
            completed.add(show);
        } else if (category.equalsIgnoreCase("watching")) {
            watching.add(show);
        } else if (category.equalsIgnoreCase("planned")) {
            planned.add(show);
        } else {
            dropped.add(show);
        }
    }

    /*
     * REQUIRES: category must be one of Library fields
     * MODIFIES: this
     * EFFECTS: removes Show to given category list, return null if not available
     */
    public String removeFromList(Show show) {
        for (List<Show> list  : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show newShow : list) {
                if (newShow.getName().equalsIgnoreCase(show.getName())) {
                    list.remove(show);
                    return "done";
                }
            }
        }
        return "That show is already not in your library";
    }

    /*
     * EFFECTS: Returns category of given show, or returns null if show not found in any list;
     */
    public String findCategoryName(Show show) {
        if (completed.contains(show)) {
            return "completed";
        } else if (watching.contains(show)) {
            return "watching";
        } else if (planned.contains(show)) {
            return "planned";
        } else if (dropped.contains(show)) {
            return "dropped";
        } else {
            return null;
        }
    }

    /*
     * EFFECTS: returns show if found in library, else returns null
     */
    public Show findShow(String showName) {
        for (List<Show> list : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show show : list) {
                if (show.getName().equalsIgnoreCase(showName)) {
                    return show;
                }
            }
        }
        return null;
    }

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