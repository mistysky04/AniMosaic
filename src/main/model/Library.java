package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Library has 4 categories for shows to be sorted into based on their watch status
// Users can find shows in any of the library fields OR add/remove shows from each field
public class Library implements Writable {
    private String title;
    private ArrayList<Show> completed;
    private ArrayList<Show> watching;
    private ArrayList<Show> planned;
    private ArrayList<Show> dropped;

    // EFFECTS: Create new instance of library with empty arrayLists and title declared
    public Library(String title) {
        this.title = title;
        completed = new ArrayList<>();
        watching = new ArrayList<>();
        planned = new ArrayList<>();
        dropped = new ArrayList<>();
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

        EventLog.getInstance().logEvent(new Event("Added " +  show.getName() + " to " + category));
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes Show from its specific category, or prompts user that show is not in library
     */
    public String removeFromList(Show show) {
        for (List<Show> list  : Arrays.asList(completed, watching, planned, dropped)) {
            for (Show newShow : list) {
                if (newShow.getName().equalsIgnoreCase(show.getName())) {
                    list.remove(show);
                    EventLog.getInstance().logEvent(new Event("Removed " +  show.getName() + " from library"));
                    return "Show Removed Successfully!";
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

    // Setters & Getters
    public String getTitle() {
        return title;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("completed", completedToJson());
        json.put("planned", plannedToJson());
        json.put("watching", watchingToJson());
        json.put("dropped", droppedToJson());
        return json;
    }

    // EFFECTS: returns Shows in Completed category as a JSON array
    private JSONArray completedToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Show s : completed) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns shows in Watching category as a JSON array
    private JSONArray watchingToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Show s : watching) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns shows in Planned category as a JSON array
    private JSONArray plannedToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Show s : planned) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns shows in Dropped category as a JSON array
    private JSONArray droppedToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Show s : dropped) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

}