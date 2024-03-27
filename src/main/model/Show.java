package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a single added show having a name, ranking, genre, total and current episode number, and comments
// Can manipulate fields of show
public class Show implements Writable {
    private String name; // Show name
    private String genre; // Genre of show
    private String comments; // User comments of show
    private int ranking; // Show ranking from 0-10
    private int currentEp; // Current episode of show user is on
    private int totalEp; // Total episodes of show

    /*
     * REQUIRES: showName and showGenre have a non-zero length, ranking values between 0-10, totalEp >= currentEp
     * EFFECTS: name of show is set to showName; genre of show is set to setGenre
     *          ranking, currentEp and totalEp assigned respective inputs
     *          Comments begins as empty String
     */
    public Show(String showName, String showGenre, String comments, int ranking, int currentEp, int totalEp) {
        name = showName;
        genre = showGenre;
        this.comments = comments;
        this.ranking = ranking;
        this.currentEp = currentEp;
        this.totalEp = totalEp;
    }

    /*
     * REQUIRES: comment.isEmpty() == false
     * MODIFIES: this
     * EFFECTS: updates comments of show
     * each call will replace old comment
     */
    public String addComments(String comment) {
        this.comments = comment;
        return comments;
    }

    /*
     * MODIFIES: this
     * EFFECTS: deletes current comment section, leaving it empty
     */
    public void deleteComments() {
        this.comments = "";
    }

    /*
     * EFFECTS: returns a string representation of the show
     */
    @Override
    public String toString() {
        String epRatio = Integer.toString(currentEp) + "/" + Integer.toString(totalEp);
        return "name: " + name + "\n\tgenre: " + genre
                + "\n\tranking: " + ranking + "\n\tepisodes: " + epRatio
                + "\n\tcomments: " + comments + "\n";
    }

    // Setters & Getters
    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public String getComments() {
        return comments;
    }

    public int getRanking() {
        return ranking;
    }

    public int getCurrentEp() {
        return currentEp;
    }

    public int getTotalEp() {
        return totalEp;
    }

    public void setCurrentEp(int num) {
        currentEp += num;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("genre", genre);
        json.put("comments", comments);
        json.put("ranking", ranking);
        json.put("currentEp", currentEp);
        json.put("totalEp", totalEp);
        return json;
    }
}
