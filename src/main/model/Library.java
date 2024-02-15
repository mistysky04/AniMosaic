package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Library {
    private ArrayList<Show> completed = new ArrayList<>();
    private ArrayList<Show> watching = new ArrayList<>();
    private ArrayList<Show> planned = new ArrayList<>();
    private ArrayList<Show> dropped = new ArrayList<>();

    // EFFECTS: Create new instance of library with empty arrayLists
    public Library() {
    }

    /*
     * REQUIRES: category must be one of Library fields
     * MODIFIES: this
     * EFFECTS: adds Show to given category list
     */
    public void addToList(Show show, String category) {
        if (category.equals("completed")) {
            completed.add(show);
        } else if (category.equals("watching")) {
            watching.add(show);
        } else if (category.equals("planned")) {
            planned.add(show);
        } else {
            dropped.add(show);
        }
    }

    /*
     * REQUIRES: category must be one of Library fields
     * MODIFIES: this
     * EFFECTS: removes Show to given category list
     */
    public void removeFromList(Show show) {
        for (List<Show> list  : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show newShow : list) {
                if (newShow.getName().equals(show.getName())) {
                    list.remove(show);
                    return;
                }
            }
        }
    }

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

    // finds and returns show if in library, otherwise null
    public Show findShow(String showName) {
        for (List<Show> list : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show show : list) {
                if (show.getName().equals(showName)) {
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