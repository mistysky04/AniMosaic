package model;

// Represents a single added show having a name, ranking, genre, total and current episode number, and comments
public class Show {
    private String name; // Show name
    private String genre; // Genre of show
    private String comments; // User comments of show
    private int ranking; // Show ranking from 0-5
    private int currentEp; // Current episode of show user is on
    private int totalEp; // Total episodes of show

    /*
     * REQUIRES: showName and showGenre have a non-zero length
     * EFFECTS: name of show is set to showName; genre of show is set to setGenre
     *          ranking, currentEp and totalEp assigned respective inputs UNLESS none given
     *          otherwise set to 0
     */
    public Show(String showName, String showGenre, int ranking, int currentEp, int totalEp) {
        name = showName;
        genre = showGenre;
        this.ranking = ranking;
        this.currentEp = currentEp;
        this.totalEp = totalEp;
    }

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
     * REQUIRES: this.comments.isEmpty() == true
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
        return "[ name = " + name + ", genre = " + genre
                + ", ranking = " + ranking + ",  current episode = " + epRatio
                + ", comments = " + comments + " ]";
    }
}
